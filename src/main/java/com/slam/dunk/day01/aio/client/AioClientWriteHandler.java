package com.slam.dunk.day01.aio.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:50
 * @Description:
 */
public class AioClientWriteHandler
        implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel clientChannel;
    private CountDownLatch latch;

    public AioClientWriteHandler(AsynchronousSocketChannel clientChannel,
                                 CountDownLatch latch) {
        this.clientChannel = clientChannel;
        this.latch = latch;
    }

    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        //check if there is data in the buffer
        if (buffer.hasRemaining()) {
            clientChannel.write(buffer, buffer, this);
        } else {
            //write done, set up the read buffer area for read action
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            //call the read handler
            clientChannel.read(readBuffer, readBuffer,
                    new AioClientReadHandler(clientChannel, latch));
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        System.err.println("send data fail");
        try {
            clientChannel.close();
            latch.countDown();
        } catch (IOException e) {
        }
    }

}

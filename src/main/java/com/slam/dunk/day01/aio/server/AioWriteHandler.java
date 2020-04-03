package com.slam.dunk.day01.aio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Author: zerongliu
 * @Date: 4/3/20 09:26
 * @Description:
 */
public class AioWriteHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel channel;

    public AioWriteHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }


    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        if (attachment.hasRemaining()) {
            channel.write(attachment, attachment, this);
        } else {
            //read data from clients
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            //read data asynchronously
            channel.read(readBuffer, readBuffer,
                    new AioReadHandler(channel));
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

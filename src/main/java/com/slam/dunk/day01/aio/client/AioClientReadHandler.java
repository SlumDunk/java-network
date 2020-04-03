package com.slam.dunk.day01.aio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:50
 * @Description:
 */
public class AioClientReadHandler
        implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel clientChannel;
    private CountDownLatch latch;

    public AioClientReadHandler(AsynchronousSocketChannel clientChannel,
                                CountDownLatch latch) {
        this.clientChannel = clientChannel;
        this.latch = latch;
    }

    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        String msg;
        try {
            msg = new String(bytes, "UTF-8");
            System.out.println("accept message:" + msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        System.err.println("read data fail...");
        try {
            clientChannel.close();
            latch.countDown();
        } catch (IOException e) {
        }
    }

}

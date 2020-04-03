package com.slam.dunk.day01.aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:50
 * @Description:
 */
public class AioAcceptHandler
        implements CompletionHandler<AsynchronousSocketChannel,
        AioServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel channel,
                          AioServerHandler serverHandler) {
        AioServer.clientCount++;
        System.out.println("number of clients" + AioServer.clientCount);
        //re-registration to handle the new clients
        serverHandler.channel.accept(serverHandler, this);
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        //set the read buffer and read handler
        channel.read(readBuffer, readBuffer,
                new AioReadHandler(channel));

    }

    @Override
    public void failed(Throwable exc, AioServerHandler serverHandler) {
        exc.printStackTrace();
        serverHandler.latch.countDown();
    }

}

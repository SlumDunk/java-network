package com.slam.dunk.day01.aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:50
 * @Description:
 */
public class AioServerHandler implements Runnable {

    public CountDownLatch latch;
    /**
     * server socket channel
     */
    public AsynchronousServerSocketChannel channel;

    public AioServerHandler(int port) {
        try {
            //set up the server socket channel
            channel = AsynchronousServerSocketChannel.open();
            //bind port
            channel.bind(new InetSocketAddress(port));
            System.out.println("Server is start,port:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        latch = new CountDownLatch(1);
        //accept connection from clients, we need to register the server socket again in the handler, so input this
        channel.accept(this, new AioAcceptHandler());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

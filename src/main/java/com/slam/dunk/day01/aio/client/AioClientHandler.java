package com.slam.dunk.day01.aio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:50
 * @Description: aio client main program
 */
public class AioClientHandler
        implements CompletionHandler<Void, AioClientHandler>, Runnable {

    /**
     * asynchronous socket channel
     */
    private AsynchronousSocketChannel clientChannel;
    private String host;
    private int port;

    /**
     * to avoid the thread stop
     */
    private CountDownLatch latch;

    public AioClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            //create a socket channel
            clientChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        // avoid the thread to stop
        latch = new CountDownLatch(1);
        clientChannel.connect(new InetSocketAddress(host, port),
                null, this);
        try {

            latch.await();
            clientChannel.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void completed(Void result, AioClientHandler attachment) {
        System.out.println("connect to server successfully!");
    }


    @Override
    public void failed(Throwable exc, AioClientHandler attachment) {
        System.err.println("connect to server fail");
        exc.printStackTrace();
        //stop the network handle thread
        latch.countDown();
        try {
            clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * send msg to server
     *
     * @param msg
     */
    public void sendMessage(String msg) {
        //convert string to byte buffer
        byte[] bytes = msg.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();


        clientChannel.write(writeBuffer, writeBuffer,
                new AioClientWriteHandler(clientChannel, latch));


    }

}

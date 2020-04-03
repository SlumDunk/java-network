package com.slam.dunk.day01.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:44
 * @Description:
 */
public class NioServerHandler implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private volatile boolean started;


    public NioServerHandler(int port) {
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            started = true;
            System.out.println("start server successfully, the port is : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        started = false;
    }

    @Override
    public void run() {
        //loop the selector
        while (started) {
            try {
                //wake up at most every 1000 ms
                selector.select(1000);
                //blocking until one event happen
                //selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        //free resources
                        //remove the channel registered on the selector
                        if (key != null) {
                            //set as not valid
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        //free resources
        if (selector != null) {
            try {
                selector.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //accept request, create new socket channel
            if (key.isAcceptable()) {
                //TODO
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                System.out.println("=========set up the socket channel connect with client");
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);

            }
            //read event
            if (key.isReadable()) {
                System.out.println("======socket channel is ready for reading");
                SocketChannel sc = (SocketChannel) key.channel();
                //create a buffer
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //copy data into buffer
                int readBytes = sc.read(buffer);
                //deal with the data
                if (readBytes > 0) {
                    //convert write mode to read mode
                    buffer.flip();
                    //create array
                    byte[] bytes = new byte[buffer.remaining()];
                    //copy data from buffer to bytes array
                    buffer.get(bytes);
                    String message = new String(bytes, "UTF-8");
                    System.out.println("the msg received by server is: " + message);
                    //business logic
                    String result = null;
                    try {
                        result = result = "Hello," + message + ",Now is "
                                + new java.util.Date(
                                System.currentTimeMillis()).toString();
                    } catch (Exception e) {
                        result = "error: " + e.getMessage();
                    }
                    //send response
                    doWrite(sc, result);
                }
                //ignore if we don't receive any data
//				else if(readBytes==0);
                //the channel is close, free resources
                else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            }

        }
    }

    /**
     * write msg to the channel
     *
     * @param channel
     * @param response
     * @throws IOException
     */
    private void doWrite(SocketChannel channel, String response)
            throws IOException {
        //string to bytes
        byte[] bytes = response.getBytes();
        //create the buffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        //copy data to buffer
        writeBuffer.put(bytes);
        //convert write mode to read mode
        writeBuffer.flip();
        //send msg
        channel.write(writeBuffer);
    }

}

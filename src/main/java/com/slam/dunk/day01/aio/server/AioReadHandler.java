package com.slam.dunk.day01.aio.server;

import com.slam.dunk.day01.Const;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:50
 * @Description:
 */
public class AioReadHandler
        implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel channel;

    public AioReadHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }


    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        //if result equals -1, the client stops the connection, close channel
        if (result == -1) {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        //flip
        attachment.flip();
        byte[] message = new byte[attachment.remaining()];
        attachment.get(message);
        try {
            System.out.println(result);
            String msg = new String(message, "UTF-8");
            System.out.println("server accept message:" + msg);
            String responseStr = Const.response(msg);
            //send response to client
            doWrite(responseStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * send msg
     *
     * @param result
     */
    private void doWrite(String result) {
        byte[] bytes = result.getBytes();
        //set up the write buffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        //write data to client
        channel.write(writeBuffer, writeBuffer,
                new AioWriteHandler(channel));
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

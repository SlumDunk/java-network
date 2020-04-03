package com.slam.dunk.day01.aio.server;

import com.slam.dunk.day01.Const;


/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:50
 * @Description:
 */
public class AioServer {
    private static AioServerHandler serverHandle;
    /**
     * number of connected clients
     */
    public volatile static long clientCount = 0;

    public static void start() {
        if (serverHandle != null) {
            return;
        }
        serverHandle = new AioServerHandler(Const.DEFAULT_PORT);
        new Thread(serverHandle, "Server").start();
    }

    public static void main(String[] args) {
        AioServer.start();
    }
}

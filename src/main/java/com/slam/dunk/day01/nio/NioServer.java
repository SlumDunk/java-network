package com.slam.dunk.day01.nio;

import com.slam.dunk.day01.Const;


/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:44
 * @Description:
 */
public class NioServer {

    private static NioServerHandler nioServerHandler;

    public static void start() {
        if (nioServerHandler != null) {
            nioServerHandler.stop();
        }
        nioServerHandler = new NioServerHandler(Const.DEFAULT_PORT);
        new Thread(nioServerHandler, "Server").start();
    }

    public static void main(String[] args) {
        start();
    }

}

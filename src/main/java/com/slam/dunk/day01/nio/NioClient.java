package com.slam.dunk.day01.nio;

import com.slam.dunk.day01.Const;

import java.util.Scanner;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:44
 * @Description:
 */
public class NioClient {

    private static NioClientHandler nioClientHandler;

    /**
     * start the thread to exchange information with server
     */
    public static void start() {
        if (nioClientHandler != null) {
            nioClientHandler.stop();
        }
        nioClientHandler = new NioClientHandler(Const.DEFAULT_SERVER_IP, Const.DEFAULT_PORT);
        new Thread(nioClientHandler, "Server").start();
    }

    /**
     * send msg to server
     *
     * @param msg
     * @return
     * @throws Exception
     */
    public static boolean sendMsg(String msg) throws Exception {
        nioClientHandler.sendMsg(msg);
        return true;
    }

    public static void main(String[] args) throws Exception {
        start();
        Scanner scanner = new Scanner(System.in);
        while (NioClient.sendMsg(scanner.next())) {
            ;
        }

    }

}

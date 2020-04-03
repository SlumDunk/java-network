package com.slam.dunk.day01.aio.client;

import com.slam.dunk.day01.Const;

import java.util.Scanner;


/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:50
 * @Description:
 */
public class AioClient {

    /**
     * IO handler
     */
    private static AioClientHandler clientHandler;

    public static void start() {
        if (clientHandler != null) {
            return;
        }
        clientHandler = new AioClientHandler(Const.DEFAULT_SERVER_IP, Const.DEFAULT_PORT);
        //start a new thread to read data from server
        new Thread(clientHandler, "Client").start();
    }

    /**
     * send msg to server
     *
     * @param msg
     * @return
     * @throws Exception
     */
    public static boolean sendMsg(String msg) throws Exception {
        if (msg.equals("q")) {
            return false;
        }
        clientHandler.sendMessage(msg + System.getProperty("line.separator"));
        return true;
    }

    public static void main(String[] args) throws Exception {
        AioClient.start();
        System.out.println("please input your msg");
        Scanner scanner = new Scanner(System.in);
        while (AioClient.sendMsg(scanner.nextLine())) {

        }
    }

}

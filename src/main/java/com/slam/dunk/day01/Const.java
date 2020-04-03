package com.slam.dunk.day01;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:50
 * @Description:
 */
public class Const {
    /**
     * port number
     */
    public static int DEFAULT_PORT = 12345;
    public static String DEFAULT_SERVER_IP = "127.0.0.1";

    /**
     * response method
     *
     * @param msg
     * @return
     */
    public static String response(String msg) {
        return "Hello," + msg + ",Now is " + new java.util.Date(
                System.currentTimeMillis()).toString();
    }
}

package com.slam.dunk.day01.buffer;

import java.nio.ByteBuffer;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:44
 * @Description:
 */
public class AllocateBuffer {
    public static void main(String[] args) {
        System.out.println("----------Test allocate--------");
        System.out.println("before allocate:" + Runtime.getRuntime().freeMemory());

        //1M memory on heap
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1000);
        System.out.println("buffer = " + buffer);

        System.out.println("after allocate: " + Runtime.getRuntime().freeMemory());

        //use direct buffer
        System.out.println("before direct allocate:" + Runtime.getRuntime().freeMemory());
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(1024 * 1000);
        System.out.println("directBuffer = ");
        System.out.println("after direct allocate: " + Runtime.getRuntime().freeMemory());

        System.out.println("----------Test wrap--------");
        byte[] bytes = new byte[32];


    }
}

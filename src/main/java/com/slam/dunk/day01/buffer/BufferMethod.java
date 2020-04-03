package com.slam.dunk.day01.buffer;

import java.nio.ByteBuffer;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:44
 * @Description:
 */
public class BufferMethod {
    public static void main(String[] args) {


        ByteBuffer buffer = ByteBuffer.allocate(20);
        //0
        buffer.put((byte) 'a');
        //1
        buffer.put((byte) 'b');
        //2
        buffer.put((byte) 'c');
        buffer.put((byte) 'd');
        buffer.put((byte) 'e');
        buffer.put((byte) 'f');
        System.out.println("before flip() " + buffer);
        //convert to read mode
        buffer.flip();
        System.out.println("after flip() buffer = " + buffer);

        System.out.println("------Test get-------------");
        //position moves
        System.out.println("before get(): " + buffer);
        System.out.println((char) buffer.get());
        System.out.println("after get(): " + buffer);

        //position doesn't move
        System.out.println("before get(Index): " + buffer);
        System.out.println((char) buffer.get(2));
        System.out.println("after get(Index): " + buffer);

        //position moves
        byte[] dst = new byte[10];
        System.out.println("before get(sr, offset, length): " + buffer);
        buffer.get(dst, 0, 2);
        System.out.println("after get(sr, offset, length): " + buffer);
        System.out.println("dst: " + new String(dst));

        //
        System.out.println("--------Test put------------");
        ByteBuffer bb = ByteBuffer.allocate(32);
        //position move
        System.out.println("before put(byte): " + bb);
        bb.put((byte) ('z'));
        System.out.println("after put(byte): " + bb);

        //position doesn't move
        System.out.println("before put(index, byte): " + bb);
        bb.put(2, (byte) ('z'));
        System.out.println("after put(index, byte): " + bb);

        System.out.println("before put(buffer): " + bb);
        ByteBuffer subBuffer = ByteBuffer.allocate(10);
        subBuffer.put((byte) ('a'));
        subBuffer.put((byte) ('b'));
        subBuffer.put((byte) ('c'));
        bb.put(subBuffer);
        System.out.println(new String(bb.array()));
        System.out.println("after put(buffer): " + bb);

        System.out.println("--------Test reset----------");
        buffer = ByteBuffer.allocate(20);
        System.out.println("buffer = " + buffer);
        //reset the position of the pointers
        buffer.clear();
        //move position pointer to index 5
        buffer.position(5);
        //record the position of the pointer position
        buffer.mark();
        //move position pointer to index 10
        buffer.position(10);
        System.out.println("before reset:" + buffer);
        //reset the position of the position pointer
        buffer.reset();
        System.out.println("after reset:" + buffer);

        System.out.println("--------Test rewind--------");
        buffer.clear();
        //move position pointer to index 10
        buffer.position(10);
        //set the limit as 15
        buffer.limit(15);
        System.out.println("before rewind:" + buffer);
        //reset the position pointer, keep the position of limit pointer
        buffer.rewind();
        System.out.println("before rewind:" + buffer);

        System.out.println("--------Test compact--------");
        buffer.clear();
        //position move to 4
        buffer.put("abcd".getBytes());
        System.out.println("before compact:" + buffer);
        System.out.println(new String(buffer.array()));
        //convert from write mode to read mode
        buffer.flip();
        System.out.println("after flip:" + buffer);
        //read from buffer
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println("after three gets:" + buffer);
        System.out.println(new String(buffer.array()));
        //the last element will be moved to the first index
        buffer.compact();
        System.out.println("after compact:" + buffer);
        System.out.println(new String(buffer.array()));


    }
}

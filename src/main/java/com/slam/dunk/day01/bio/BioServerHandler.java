package com.slam.dunk.day01.bio;

import com.slam.dunk.day01.Const;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:46
 * @Description:
 */
public class BioServerHandler implements Runnable {
    private Socket socket;

    public BioServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (// the input stream and output stream of the socket
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(),
                     true)) {
            String message;
            String result;
            // read data from the client
            while ((message = in.readLine()) != null) {
                System.out.println("Server accept message:" + message);
                result = Const.response(message);
                //send msg to the client
                out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}

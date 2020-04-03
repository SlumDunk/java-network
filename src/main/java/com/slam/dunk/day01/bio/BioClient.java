package com.slam.dunk.day01.bio;

import com.slam.dunk.day01.Const;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:44
 * @Description:
 */
public class BioClient {
    public static void main(String[] args) throws InterruptedException,
            IOException {
        //set up the client socket
        Socket socket = new Socket(Const.DEFAULT_SERVER_IP, Const.DEFAULT_PORT);
        System.out.println("please input your msg");
        //start the thread to read msg from server
        new ReadMsg(socket).start();
        PrintWriter pw = null;
        //read data from client, and send to server
        while (true) {
            pw = new PrintWriter(socket.getOutputStream());
            pw.println(new Scanner(System.in).next());
            pw.flush();
        }
    }

    /**
     * read data from server
     */
    private static class ReadMsg extends Thread {
        Socket socket;

        public ReadMsg(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            //input stream of socket
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()))) {
                String line = null;
                //read data
                while ((line = br.readLine()) != null) {
                    System.out.printf("%s\n", line);
                }
            } catch (SocketException e) {
                System.out.printf("%s\n", "the server disconnects");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                clear();
            }
        }

        /**
         * clear the resources
         */
        private void clear() {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.slam.dunk.day01.bio;

import com.slam.dunk.day01.Const;
import com.slam.dunk.day01.bio.BioServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Author: zerongliu
 * @Date: 4/2/20 22:45
 * @Description:
 */
public class BioServer {
    /**
     * server socket
     */
    private static ServerSocket server;
    /**
     * thread pool, handle the requests
     */
    private static ExecutorService executorService
            = new ThreadPoolExecutor(5, 5,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    private static void start() throws IOException {

        try {

            server = new ServerSocket(Const.DEFAULT_PORT);
            System.out.println("start server successfully, port is ：" + Const.DEFAULT_PORT);
            while (true) {

                Socket socket = server.accept();
                System.out.println("new client connected----");
                //start a new task
                executorService.execute(new BioServerHandler(socket));
            }
        } finally {
            if (server != null) {
                server.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        start();
    }
}

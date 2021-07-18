package com.chenwb.service;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : ChenWeiBin
 * @date : 2021/7/17
 */
public class SocketServiceHelper {
    private ServerSocket mSocketServer;
    private AtomicInteger mClientCount;

    public void startServer(int port) {
        try {
            mSocketServer = new ServerSocket(port);
            mClientCount = new AtomicInteger();
            logServer("**************************************************");
            logServer("服务器已经启动，端口：" + port);
            TaskSocket myTaskServer;
            do {
                Socket socket = mSocketServer.accept();
                int count = mClientCount.incrementAndGet();
                String hostAddress = socket.getInetAddress().getHostAddress();
                logServer("客户端已连接：" + count + " IP: " + hostAddress);
                logServer("**************************************************");

                myTaskServer = new SimpleTaskSocket(socket, hostAddress) {
                    @Override
                    protected void onClientClose() {
                        mClientCount.decrementAndGet();
                    }
                };
                myTaskServer.start();

                if (!myTaskServer.isAlive()) {
                    mSocketServer.close();
                }
            } while (myTaskServer.isAlive());

        } catch (IOException e) {
            logServer(e.getMessage());
            e.printStackTrace();
            System.exit(3);
        } finally {
            try {
                if (mSocketServer != null) {
                    mSocketServer.close();
                }
            } catch (IOException e) {
                logServer(e.getMessage());
                e.printStackTrace();
                System.exit(4);
            }
        }
    }

    public static void logServer(String message) {
        System.out.println("server : " + message);
    }

    public static void main(String[] args) {
        new SocketServiceHelper().startServer(5344);
    }
}

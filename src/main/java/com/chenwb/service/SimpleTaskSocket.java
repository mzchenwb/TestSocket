package com.chenwb.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author : ChenWeiBin
 * @date : 2021/7/17
 */
public class SimpleTaskSocket extends TaskSocket {
    public SimpleTaskSocket(Socket socket, String ip) {
        super(socket, ip);
    }

    @Override
    protected void readWriteSocket(InputStream inputStream, OutputStream outputStream) {

    }
}

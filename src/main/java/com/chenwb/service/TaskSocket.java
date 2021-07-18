package com.chenwb.service;

import com.chenwb.utils.IOUtils;

import java.io.*;
import java.net.Socket;

/**
 * @author : ChenWeiBin
 * @date : 2021/7/17
 */
public abstract class TaskSocket extends Thread {
    private final String mIp;
    private final Socket mSocket;
    private String mUserName;

    private DataInputStream myClientInputStream;
    private DataOutputStream myClientOutputStream;

    public TaskSocket(Socket socket, String ip) {
        this.mSocket = socket;
        this.mIp = ip;
    }

    protected boolean validateUserInfo(String userInfo) {
        mUserName = userInfo;
        return userInfo != null;
    }

    @Override
    public void run() {
        try {
            myClientInputStream = new DataInputStream(mSocket.getInputStream());
            myClientOutputStream = new DataOutputStream(mSocket.getOutputStream());

            String userInfo = myClientInputStream.readUTF();
            if (validateUserInfo(userInfo)) {
                SocketServiceHelper.logServer("用户:" + mUserName + " 连接成功！");
                myClientOutputStream.writeBoolean(true);
                readWriteSocket(mSocket.getInputStream(), mSocket.getOutputStream());
            }
            else {
                myClientOutputStream.writeBoolean(false);
                myClientOutputStream.writeUTF("用户信息错误：" + userInfo);
                SocketServiceHelper.logServer("用户信息错误：" + userInfo);
            }
        } catch (Exception ex) {
            SocketServiceHelper.logServer(ex.getMessage() + " ip:" + mIp + " user:" + mUserName);
            ex.printStackTrace();
        } finally {
            IOUtils.closeIO(myClientInputStream);
            IOUtils.closeIO(myClientOutputStream);
            IOUtils.closeIO(mSocket);
            onClientClose();
        }
    }

    protected abstract void readWriteSocket(InputStream inputStream, OutputStream outputStream);

    void onClientClose() {
    }



    public String getIp() {
        return mIp;
    }

    public Socket getSocket() {
        return mSocket;
    }

    public String getUserName() {
        return mUserName;
    }
}
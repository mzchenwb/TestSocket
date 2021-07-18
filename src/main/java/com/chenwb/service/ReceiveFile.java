package com.chenwb.service;

import com.chenwb.utils.IOUtils;

import java.io.*;

/**
 * @author : ChenWeiBin
 * @date : 2021/7/18
 */
public class ReceiveFile {
    public void receive(InputStream inputStream, File outFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        byte[] bytes = new byte[4096];
        int length;
        while ((length = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, length);
        }
        IOUtils.closeIO(fileOutputStream);
    }
}

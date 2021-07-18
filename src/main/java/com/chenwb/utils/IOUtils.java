package com.chenwb.utils;

import com.chenwb.service.SocketServiceHelper;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author : ChenWeiBin
 * @date : 2021/7/18
 */
public class IOUtils {

    public static void closeIO(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

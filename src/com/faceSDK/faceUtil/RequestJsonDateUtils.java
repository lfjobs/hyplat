package com.faceSDK.faceUtil;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 获取取请求中Josn传输数据
 */
public class RequestJsonDateUtils {

    //获取请求体中的数据
    public  static String  getRequestJsonStr() {
        HttpServletRequest request = ServletActionContext.getRequest();
        InputStream inputStream;
        String strResponse = "";
        try {
            inputStream = request.getInputStream();
            String strMessage = "";
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            while ((strMessage = reader.readLine()) != null) {
                strResponse += strMessage;
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }
}


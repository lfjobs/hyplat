package com.tiantai.wfj.common;

import java.io.PrintWriter;

public class ClientUtils {

    public static void sendResponse(PrintWriter out,String json){

        out.write(json);
        out.flush();
        out.close();
    }
}

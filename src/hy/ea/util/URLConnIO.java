package hy.ea.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

/**
 *
 * @项目名称:xiaotuan
 * @类名称:URLConnIO
 * @类描述:
 * @创建人:苗有虎
 * @创建时间:Sep 18, 20103:18:48 PM
 * @修改人:Administrator
 * @修改时间:Sep 18, 20103:18:48 PM
 *
 * @修改备注:
 * @version
 */
public class URLConnIO {
    public URLConnIO() {

    }

    public static InputStream getSoapInputStream(String requestAddress,
                                                 String requestData) {
        InputStream is = null;
        try {
            URL U = new URL(requestAddress);
            URLConnection conn = U.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection)conn;
            byte[] bts = requestData.getBytes();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");//application/x-java-serialized-object
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setConnectTimeout(3000);
            httpUrlConnection.setRequestProperty("Content-Length",Integer.toString(bts.length));
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.connect();
            OutputStream outStrm = httpUrlConnection.getOutputStream();

            outStrm.write(bts, 0, bts.length);
            outStrm.flush();
            outStrm.close();
            is = httpUrlConnection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return is;
    }
}
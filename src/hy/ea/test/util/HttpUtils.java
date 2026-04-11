package hy.ea.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.test.other.IPBean;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Asche
 * @github: https://github.com/asche910
 * @date 2019年1月19日
 */
public class HttpUtils {


    public static String getResponseContent(String url) {
        try {
            return getResponseContent(url, null, null);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return null;
    }

    public static String getResponseContent(String url, IPBean ipBean) {
        try {
            return getResponseContent(url, null, ipBean);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return null;
    }

    /**
     * @param url
     * @param headerMap 请求头部
     * @param ipBean
     * @return
     * @throws Exception
     */
    public static String getResponseContent(String url, Map<String, List<String>> headerMap, IPBean ipBean) throws Exception {
        HttpURLConnection connection = null;

        // 设置代理
        if (ipBean != null) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipBean.getIp(), ipBean.getPort()));

            connection = (HttpURLConnection) new URL(url).openConnection(proxy);

            if (ipBean.getType() == IPBean.TYPE_HTTPS) {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
                /*connection.setSSLSocketFactory(sslContext.getSocketFactory());
                connection.setHostnameVerifier(new TrustAnyHostnameVerifier());*/
            }
        }

        if (connection == null)
            connection = (HttpURLConnection) new URL(url).openConnection();


        connection.setRequestProperty("contentType", "UTF-8");
        connection.setConnectTimeout(5 * 1000);
        connection.setRequestMethod("GET");
        // 添加请求头部
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        if (headerMap != null) {
            Iterator<Map.Entry<String, List<String>>> iterator = headerMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> entry = iterator.next();
                List<String> values = entry.getValue();
                for (String value : values)
                    connection.setRequestProperty(entry.getKey(), value);
            }
        }

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        inputStream.close();
        return stringBuilder.toString();
    }


    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}

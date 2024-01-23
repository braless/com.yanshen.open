package com.yanshen.common.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SmsClientUtil {

    private static  final Logger LOG = LoggerFactory.getLogger(SmsClientUtil.class);

    public static String sendPush(String targetPhone,String comntent) {
        try {
            StringBuilder url = new StringBuilder("http://116.62.244.37/qdplat/SMSSendYD?usr=6119&pwd=ian_un2020");
            url.append("&mobile=");
            url.append(String.join(",", targetPhone));
            url.append("&sms=");
            url.append(URLEncoder.encode(comntent,"GBK"));
            URL uri = new URL(url.toString());
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            byte[] bytes = new byte[1024 * 2000];
            int index = 0;
            int count = inputStream.read(bytes, index, 1024 * 2000);
            while (count != -1) {
                index += count;
                count = inputStream.read(bytes, index, 1);
            }
            String htmlContent = new String(bytes, "UTF-8");
            LOG.info(targetPhone+"----"+comntent);
            LOG.info(url.toString());
            connection.disconnect();
            return htmlContent.trim();
        } catch (Exception e) {
            LOG.error("",e);
            return null;
        }
    }

}

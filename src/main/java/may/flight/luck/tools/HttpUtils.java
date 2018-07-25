package may.flight.luck.tools;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    public static String formPost(String url, Map<String,String> params) {
        return formPost(url, params, "utf-8");
    }

    public static String formPost(String url, Map<String,String> params, String charset) {
        if (StringUtils.isBlank(url)
                || StringUtils.isBlank(charset)
                || null == params) {
            return null;
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        for (String key:params.keySet()) {
            if (StringUtils.isBlank(key)) {
                continue;
            }
            String value = params.get(key);
            if (StringUtils.isBlank(value)) {
                continue;
            }
            list.add(new BasicNameValuePair(key, value));
        }
        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(list, charset));
            HttpResponse response = httpClient.execute(post);
            HttpEntity responseEntity = response.getEntity();
            if (null == responseEntity) {
                return null;
            }
            return EntityUtils.toString(responseEntity);
        }catch (Exception e) {

        }finally {
            try {
                httpClient.close();
            }catch (Exception e) {

            }
        }
        return null;
    }

    public static String formPostWithHeader(String url, Map<String, String> headerMap, Map<String, String> body, String charset){
        if (StringUtils.isBlank(url) || null == headerMap|| null == body) {
            return null;
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        for (Map.Entry<String,String> entry : headerMap.entrySet()) {
            if (null == entry) {
                continue;
            }
            post.setHeader(entry.getKey(), entry.getValue());
        }

        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        for (String key : body.keySet()) {
            if (StringUtils.isBlank(key)) {
                continue;
            }
            String value = body.get(key);
            if (StringUtils.isBlank(value)) {
                continue;
            }
            list.add(new BasicNameValuePair(key, value));
        }
        try{
            post.setEntity(new UrlEncodedFormEntity(list, charset));
            HttpResponse response = httpClient.execute(post);
            HttpEntity responseEntity = response.getEntity();
            if (null == responseEntity) {
                return null;
            }
            return EntityUtils.toString(responseEntity);
        }catch (Exception e){

        }finally {
            try {
                httpClient.close();
            }catch (Exception e) {

            }
        }
        return null;
    }

    public static String xmlPost(String url, String content, String charset){
        if (StringUtils.isBlank(url) || StringUtils.isBlank(content)) {
            return null;
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(content, charset));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (null == responseEntity) {
                return null;
            }
            return EntityUtils.toString(responseEntity);
        }catch (Exception e){

        }
        return null;
    }



    public static HttpClient getHttpsClient(String credentialPath, String credentialPassword) throws Exception {
        if (StringUtils.isBlank(credentialPassword)
                || StringUtils.isBlank(credentialPath)) {
            return null;
        }
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream inputStream = new FileInputStream(credentialPath);
        try {
            keyStore.load(inputStream, credentialPassword.toCharArray());
        } finally {
            inputStream.close();
        }
        SSLContext sslContext = SSLContexts
                .custom()
                .loadKeyMaterial(keyStore, credentialPassword.toCharArray())
                .build();
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(
                sslContext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLSocketFactory(factory)
                .build();
        return httpClient;
    }

    public static String httpGet(String url, int timeOut) {
        if (StringUtils.isBlank(url)) {
            return null;
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        timeOut = timeOut == 0 ? 60 : timeOut;
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeOut * 1000)
                    .setSocketTimeout(timeOut * 1000)
                    .build();
            httpGet.setConfig(config);
            HttpResponse response =  httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (null == responseEntity) {
                return null;
            }
            return EntityUtils.toString(responseEntity);
        }catch (Exception e) {

        }finally {
            try {
                httpClient.close();
            }catch (Exception e) {

            }
        }
        return null;
    }


    public static String httpPost(HttpClient httpClient, String body, String url, String contentType, String charset) {
        if (StringUtils.isBlank(url)
                || StringUtils.isBlank(body)
                || StringUtils.isBlank(contentType)
                || StringUtils.isBlank(charset)
                || null == httpClient ){
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        try {
            StringEntity entity = new StringEntity(body, charset);
            entity.setContentType(contentType);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (null == responseEntity) {
                return null;
            }
            return EntityUtils.toString(responseEntity);
        }catch (Exception e) {

        }
        return null;
    }

    public static String sendGetRequest(String url, String contentType) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        if (url.contains(" ")) {
            url = url.replaceAll(" ", "%20");
        }
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        if (StringUtils.isBlank(contentType)) {
            httpGet.setHeader("Content-Type", "text/htmls;charset=UTF-8");
        }else {
            httpGet.setHeader("Content-Type", contentType);
        }

        BufferedReader bufferedReader = null;

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
            StringBuilder responseString = new StringBuilder();
            String result = bufferedReader.readLine();
            while (result != null) {
                responseString.append(result);
                result = bufferedReader.readLine();
            }
            return responseString.toString();
        } catch (Exception e) {

            return null;
        }finally{
            try {
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (IOException e) {

            }
        }
    }

    private static class DefaultTrustManager implements X509TrustManager {
        private DefaultTrustManager() {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }



}

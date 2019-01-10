package com.gyanbooster.api_calling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gyanbooster.constants.Constants;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 1/31/2018.
 */

public class APIClient {


    public static Retrofit getClient(String baseUrl) {
        try {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            httpClient.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            httpClient.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("key", Constants.HEADER_KEY);
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();
//            Gson gson = new GsonBuilder().setLenient().create();
            return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Retrofit getClientWithMultipleHeader(String baseUrl) {
        try {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            httpClient.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            httpClient.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("key", Constants.HEADER_KEY)
                            .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();
//            Gson gson = new GsonBuilder().setLenient().create();
            return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Retrofit getClientLogin(String baseUrl) {
        try {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("key", Constants.HEADER_KEY)
                            .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();

            Gson gson = new GsonBuilder().setLenient().create();

            return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

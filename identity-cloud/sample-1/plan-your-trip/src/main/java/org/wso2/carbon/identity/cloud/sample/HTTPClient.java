/**
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.identity.cloud.sample;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class HTTPClient {

    private ThreadSafeClientConnManager connManager;
    private DefaultHttpClient client;

    public HTTPClient() {
        client = (DefaultHttpClient) getHttpClient();
//        connManager = getHTTPConnectionManager();
//        client = configureHTTPClient(connManager);
    }

    public HttpResponse doGet(String url, String token) throws IOException {
        HttpUriRequest request = new HttpGet(url);
        addSecurityHeaders(request, token);
        return client.execute(request);
    }

    private DefaultHttpClient configureHTTPClient(ThreadSafeClientConnManager connManager) {
        connManager.setDefaultMaxPerRoute(1000);
        DefaultHttpClient client = new DefaultHttpClient(connManager);
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);
        client.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                return false;
            }
        });
        return client;
    }

    public HttpResponse doPost(String url, String token, final String payload, String contentType) throws IOException {
        HttpUriRequest request = new HttpPost(url);
        addSecurityHeaders(request, token);

        HttpEntityEnclosingRequest entityEncReq = (HttpEntityEnclosingRequest) request;
        EntityTemplate ent = new EntityTemplate(new ContentProducer() {
            public void writeTo(OutputStream outputStream) throws IOException {
                outputStream.write(payload.getBytes());
                outputStream.flush();
            }
        });
        ent.setContentType(contentType);
        entityEncReq.setEntity(ent);
        return client.execute(request);
    }

    public String getResponsePayload(HttpResponse response) throws IOException {
        StringBuffer buffer = new StringBuffer();
        InputStream in = null;
        try {
            if (response.getEntity() != null) {
                in = response.getEntity().getContent();
                int length;
                byte[] tmp = new byte[2048];
                while ((length = in.read(tmp)) != -1) {
                    buffer.append(new String(tmp, 0, length));
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return buffer.toString();
    }

    private ThreadSafeClientConnManager getHTTPConnectionManager() {
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        X509HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        socketFactory.setHostnameVerifier(hostnameVerifier);

        SchemeRegistry supportedSchemes = new SchemeRegistry();
        supportedSchemes.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        supportedSchemes.register(new Scheme("https",443, socketFactory));

        return new ThreadSafeClientConnManager(supportedSchemes);
    }

    private void addSecurityHeaders(HttpRequest request, String token) {
        if (token != null) {
            request.setHeader(HttpHeaders.AUTHORIZATION, token);
        }
    }

    private HttpClient getHttpClient() {
        SchemeRegistry registry = new SchemeRegistry();
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        X509HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        socketFactory.setHostnameVerifier(hostnameVerifier);

        registry.register(new Scheme("https", 443, socketFactory));
        registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

        HttpParams params = new BasicHttpParams();
        ThreadSafeClientConnManager tcm = new ThreadSafeClientConnManager(registry);
        return new DefaultHttpClient(tcm, params);


//        SSLContext ctx = null;
//        try {
//            ctx = SSLContext.getInstance("TLS");
//            X509TrustManager tm = new X509TrustManager() {
//
//                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//                }
//
//                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//                }
//
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//            };
//            ctx.init(null, new TrustManager[]{tm}, null);
//            SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            ClientConnectionManager ccm = base.getConnectionManager();
//            SchemeRegistry sr = ccm.getSchemeRegistry();
//            sr.register(new Scheme("https", 443, ssf));
//
//            client = new DefaultHttpClient(ccm, base.getParams());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }


    }
}

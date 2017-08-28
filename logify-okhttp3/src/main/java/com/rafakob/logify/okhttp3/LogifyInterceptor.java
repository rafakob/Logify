package com.rafakob.logify.okhttp3;


import android.support.annotation.NonNull;

import com.rafakob.logify.repository.LogsRepository;
import com.rafakob.logify.repository.entity.NetworkLog;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

public class LogifyInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private final boolean enabled;

    public LogifyInterceptor() {
        this.enabled = true;
    }

    public LogifyInterceptor(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!enabled) {
            return chain.proceed(chain.request());
        }

        final Connection connection = chain.connection();
        final Request request = chain.request();
        final Response response = chain.proceed(request);
        final RequestBody requestBody = request.body();
        final ResponseBody responseBody = response.body();

        NetworkLog log = new NetworkLog();

        log.setRequestMethod(request.method());
        log.setResponseCode(response.code());
        log.setResponseMessage(response.message());
        log.setUrl(request.url().toString());
        log.setHost(request.url().host());
        log.setPath("/" + join(request.url().encodedPathSegments(), "/"));
        log.setHttps(request.isHttps());
        log.setProtocol(connection != null && connection.protocol() != null ? connection.protocol().toString() : Protocol.HTTP_1_1.toString());
        log.setTimestamp(response.sentRequestAtMillis());
        log.setDuration(response.receivedResponseAtMillis() - response.sentRequestAtMillis());

        /* Request body */
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            log.setRequestBody(buffer.readString(charset));
        }
        /* Request params */
        for (String param : request.url().queryParameterNames()) {
            log.getRequestParams().put(param, join(request.url().queryParameterValues(param), ","));
        }

        /* Request headers */
        for (int i = 0, count = request.headers().size(); i < count; i++) {
            log.getRequestHeaders().put(request.headers().name(i), request.headers().value(i));
        }

        /* Response body */
        if (HttpHeaders.hasBody(response) && responseBody != null) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            log.setResponseBody(buffer.clone().readString(charset));
        }

        /* Response headers */
        for (int i = 0, count = response.headers().size(); i < count; i++) {
            log.getResponseHeaders().put(response.headers().name(i), response.headers().value(i));
        }

        LogsRepository.getInstance().insertNetworkLog(log);

        return response;
    }

    private String join(List<String> list, String d) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));

            if (i != list.size() - 1) {
                sb.append(d);
            }
        }
        return sb.toString();
    }

    private String capitalize(final String text) {
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}
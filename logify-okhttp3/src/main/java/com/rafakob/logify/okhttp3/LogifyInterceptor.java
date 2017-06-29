package com.rafakob.logify.okhttp3;

import com.rafakob.logify.repository.LogsRepository;
import com.rafakob.logify.repository.entity.NetworkLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LogifyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();
        final long t1 = System.nanoTime();
        final Response response = chain.proceed(request);
        final long t2 = System.nanoTime();

        NetworkLog log = new NetworkLog();
        log.setDuration(t2 - t1);

        LogsRepository.getInstance().insertNetworkLog(log);


        return response;
    }


//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//
//        long t1 = System.nanoTime();
//
//        Response response = chain.proceed(request);
//
//
//        long t2 = System.nanoTime();
//
//        NetworkLog networkLog = new NetworkLog();
//        networkLog.setDate(new Date().getTime());
//        networkLog.setDuration((t2 - t1) / 1e6d);
//        networkLog.setErrorClientDesc("");
//        networkLog.setHeaders(String.valueOf(response.headers()));
//        networkLog.setRequestType(request.method());
//        networkLog.setResponseCode(String.valueOf(response.code()));
//        String body = response.body().string();
//        networkLog.setResponseData(body);
//        networkLog.setUrl(String.valueOf(request.url()));
//        networkLog.setPostData(bodyToString(request));
//
//        MediaType contentType = response.body().contentType();
//
//        networkLogDao.insert(networkLog);
//
//        return response.newBuilder().body(ResponseBody.create(contentType,body)).build();
//    }
//
//    private static String bodyToString(final Request request){
//
//        try {
//            final Request copy = request.newBuilder().build();
//            final Buffer buffer = new Buffer();
//            copy.body().writeTo(buffer);
//            return buffer.readUtf8();
//        } catch (final Exception e) {
//            return "";
//        }
//    }
}

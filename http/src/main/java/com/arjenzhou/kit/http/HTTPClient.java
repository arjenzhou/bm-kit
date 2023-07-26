package com.arjenzhou.kit.http;

import com.arjenzhou.kit.base.util.JSONUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTTP Client
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/26
 */
public class HTTPClient {
    private static final Pattern HTTP_PROTOCOL_PATTERN = Pattern.compile("^https?://\\S+.\\S+");
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();
    /**
     * JSON MediaType
     */
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /**
     * do a HTTP Get
     *
     * @param url   url to request
     * @param clazz class of the response body type
     * @param <R>   type of result
     * @return result
     * @throws IOException when network failure occurred
     */
    public static <R> R get(String url, Class<R> clazz) throws IOException {
        Request request = buildGetRequest(url);
        return requestAndParseBody(request, clazz);
    }

    /**
     * do a HTTP Post
     *
     * @param url         url to request
     * @param requestBody request body
     * @param clazz       class of the response body type
     * @param <S>         type request body
     * @param <R>         type of result
     * @return result
     * @throws IOException when network failure occurred
     */
    public static <S, R> R post(String url, S requestBody, Class<R> clazz) throws IOException {
        Request request = buildPostRequest(url, requestBody);
        return requestAndParseBody(request, clazz);
    }


    /**
     * do a request, and parse the response to specific type
     *
     * @param request request object
     * @param clazz   class of response body type
     * @param <T>     type of response body
     * @return parsed response body
     * @throws IOException when network failure occurred
     */
    private static <T> T requestAndParseBody(Request request, Class<T> clazz) throws IOException {
        Response response = null;
        ResponseBody responseBody = null;
        try {
            response = HTTP_CLIENT.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            responseBody = response.body();
            if (responseBody == null) {
                return null;
            }
            return JSONUtil.fromJSON(responseBody.string(), clazz);
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
            if (response != null) {
                response.close();
            }
        }
    }


    /**
     * do Get in asynchronous mode
     *
     * @param url      to request
     * @param callback to handle success or fail
     */
    public static void doGetAsync(String url, Callback callback) {
        Request request = buildGetRequest(url);
        HTTP_CLIENT.newCall(request).enqueue(callback);
    }

    /**
     * do Post in asynchronous mode
     *
     * @param <S>          type of response body
     * @param url          to request
     * @param callback     to handle success or fail
     * @param requestBody response body
     */
    public static <S> void doPostAsync(String url, S requestBody, Callback callback) {
        Request request = buildPostRequest(url, requestBody);
        HTTP_CLIENT.newCall(request).enqueue(callback);
    }

    /**
     * check whether the request url is a legal url.
     *
     * @param url to request
     */
    private static void checkURL(String url) {
        Matcher matcher = HTTP_PROTOCOL_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("illegal url: " + url);
        }
    }

    /**
     * build Request from url
     *
     * @param url the url to request
     * @return Request
     */
    private static Request buildGetRequest(String url) {
        checkURL(url);
        return new Request.Builder()
                .url(url)
                .build();
    }

    /**
     * build Request from url and requestBody
     *
     * @param url         the url to request
     * @param <S>         type of request body
     * @param requestBody the request body to request
     * @return Request
     */
    public static <S> Request buildPostRequest(String url, S requestBody) {
        checkURL(url);
        String json = JSONUtil.toJSON(requestBody);
        if (json == null) {
            json = "";
        }
        RequestBody body = RequestBody.create(json, JSON);
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

}

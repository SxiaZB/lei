package com.lei.smart.im;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.*;

public class RequestParser {

    /**
     * 解析请求参数
     * @return 包含所有请求参数的键值对, 如果没有参数, 则返回空Map
     *
     * @throws
     * @throws IOException
     */
    public static Map<String, String> parse(FullHttpRequest fullReq) throws Exception {
        HttpMethod method = fullReq.method();

        Map<String, String> parmMap = new HashMap<>();

        if (HttpMethod.GET == method) {
            // 是GET请求
            QueryStringDecoder decoder = new QueryStringDecoder(fullReq.uri());
            String uri = decoder.uri();
            if(StringUtils.isBlank(uri)){
                return parmMap;
            }
            String keyValue = uri.split("/")[2];
            if(StringUtils.isBlank(keyValue))return parmMap;
            parse(parmMap,keyValue);
        } else if (HttpMethod.POST == method) {
            // 是POST请求
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(fullReq);
            decoder.offer(fullReq);

            List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();

            for (InterfaceHttpData parm : parmList) {

                Attribute data = (Attribute) parm;
                parmMap.put(data.getName(), data.getValue());
            }

        } else {
            // 不支持其它方法
//            throw new MethodNotSupportedException(""); // 这是个自定义的异常, 可删掉这一行
        }

        return parmMap;
    }
    private static void parse(Map<String, String> map, String str) {
        if(Objects.isNull(str) || str.isEmpty()) return ;
        Arrays.stream(str.split("&"))
                .filter(kv -> kv.contains("="))
                .map(kv -> kv.split("="))
                .forEach(array -> map.put(array[0], array[1]));
    }

    public static void main(String[] args) {
        String s="/ed/daf=124&dadfa=23";
        String[] split = s.split("/");
        for (String s1 : split) {
            System.out.println(s1);
        }
        System.out.println(split[2]);
    }

}

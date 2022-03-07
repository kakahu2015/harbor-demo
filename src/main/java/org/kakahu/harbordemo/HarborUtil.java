package org.kakahu.harbordemo;


import org.apache.http.HttpEntity;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.IOException;

import java.util.Base64;

import java.util.Map;

@Component
public class HarborUtil {



    //Harbor的登录地址
    @Value("${harbor.login_address}")
    private String HARBOR_LOGIN_ADDRESS;

    //Harbor登录用户名
    @Value("${harbor.username}")
    private String HARBOR_USERNAME;

    //Harbor登录密码
    @Value("${harbor.password}")
    private String HARBOR_PASSWORD;


    @Value("${harbor.project}")
    private String PROJECT_NAME;




    //////kaak
    public  String getTag(String image,String tag) {


        String flag="error";
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        String uri = "/api/repositories/" + PROJECT_NAME + "/" + image + "/tags/" + tag;

        HttpGet httpGet = new HttpGet(HARBOR_LOGIN_ADDRESS+uri);

        httpGet.setHeader("Content-Type", "application/json");

        httpGet.setHeader("Authorization", "Basic "+Base64.getUrlEncoder().encodeToString((HARBOR_USERNAME + ":" + HARBOR_PASSWORD).getBytes()));

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                String  STR_INFO=EntityUtils.toString(responseEntity);
                System.out.println("响应内容为:" + STR_INFO);
                //////////////////////////
                Map mapTypes = JSON.parseObject(STR_INFO);
                // System.out.println("这个是用JSON类的parseObject来解析JSON字符串!!!"+mapTypes.size());
                //System.out.println("key为："+"name的值为："+mapTypes.get("name"));
                if( mapTypes.get("name") !=null){
                    flag=(String) mapTypes.get("name");
                }
                /*for (Object obj : mapTypes.keySet()){
                    System.out.println("key为："+obj+"值为："+mapTypes.get(obj));
                }*/
                ///////////////////////////
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }


}




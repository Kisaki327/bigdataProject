package com.ruoyi.system.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ruoyi.framework.config.AiProperties;
import com.ruoyi.system.domain.WenXinYiYanResponse;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class WenXinYiYanService {

    private static final Logger log = LoggerFactory.getLogger(WenXinYiYanService.class);


    private final Gson gson = new Gson();

    public String chat(String userMessage) {
        // 创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        // 构建请求体
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "ernie-4.5-8k-preview");

        JsonArray messages = new JsonArray();
        messages.add(createMessage("system", "你是一个AI图表分析助手"));
        messages.add(createMessage("user", userMessage));
        requestBody.add("messages", messages);

        // 添加请求参数
        requestBody.addProperty("maxTokens", 2048); // 最大token数（可以根据实际需求调整）
        requestBody.addProperty("stream", false);

        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json")
        );

        // 构建请求
        Request request = new Request.Builder()
                .url("https://qianfan.baidubce.com/v2/chat/completions")  // api地址
                .addHeader("Authorization", "Bearer " + "bce-v3/ALTAK-fkUKVCLonaGF507KSaweT/e97fda421eea59cf6722d4cc3f83a6af42ce33bb") // 在这里加入API密钥
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("文心一言响应失败，状态码: {}, 内容: {}", response.code(), response.body() != null ? response.body().string() : "");
                return "AI接口请求失败：" + response.code();
            }
            String responseBody = response.body() != null ? response.body().string() : "";
            log.info("文心一言响应内容: {}", responseBody);

            // 解析返回的JSON数据
            WenXinYiYanResponse wxResponse = gson.fromJson(responseBody, WenXinYiYanResponse.class);
            // 获取返回内容
            String content = wxResponse.getChoices().get(0).getMessage().getContent();
            return content != null ? content : "没有返回内容";
        } catch (IOException e) {
            log.error("调用文心一言服务异常", e);
            return "AI服务异常：" + e.getMessage();
        }
    }

    private JsonObject createMessage(String role, String content) {
        JsonObject message = new JsonObject();
        message.addProperty("role", role);
        message.addProperty("content", content);
        return message;
    }

}

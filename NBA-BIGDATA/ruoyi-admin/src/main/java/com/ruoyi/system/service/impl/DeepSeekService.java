package com.ruoyi.system.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ruoyi.framework.config.AiProperties;
import com.ruoyi.system.domain.DeepSeekResponse;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DeepSeekService {

    private static final Logger log = LoggerFactory.getLogger(DeepSeekService.class);

    @Autowired
    private AiProperties aiProperties;

    private final Gson gson = new Gson();

    public String chat(String userMessage) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", aiProperties.getModel());

        JsonArray messages = new JsonArray();
        messages.add(createMessage("system", "你是一个AI图表分析助手"));
        messages.add(createMessage("user", userMessage));
        requestBody.add("messages", messages);

        requestBody.addProperty("maxTokens", aiProperties.getMaxTokens());
        requestBody.addProperty("stream", false);

//        JsonObject streamOptions = new JsonObject();
//        streamOptions.addProperty("includeUsage", true);
//        streamOptions.addProperty("continuousUsageStats", true);
//        requestBody.add("streamOptions", streamOptions);

        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(aiProperties.getApiUrl())
                .addHeader("Authorization", "Bearer " + aiProperties.getApiKey())
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("DeepSeek响应失败，状态码: {}, 内容: {}", response.code(), response.body() != null ? response.body().string() : "");
                return "AI接口请求失败：" + response.code();
            }
            String responseBody = response.body() != null ? response.body().string() : "";
            log.info("DeepSeek响应内容: {}", responseBody);

            DeepSeekResponse dsResponse = gson.fromJson(responseBody, DeepSeekResponse.class);
            List<DeepSeekResponse.Choice> choices = dsResponse.getChoices();
            if (choices != null && !choices.isEmpty()) {
                return choices.get(0).getMessage().getContent();
            }
            return "AI接口无返回内容";

        } catch (IOException e) {
            log.error("调用DeepSeek服务异常", e);
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

package com.example.examplemod.Commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import scala.util.parsing.json.JSONArray;
import scala.util.parsing.json.JSONObject;
import com.google.gson.JsonParser;
import java.net.URLEncoder;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;




public class ChatGpt {
    private static final String API_KEY = "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"; // Замените на ваш API KEY

    public static String getChatGptResponse(String prompt) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("https://api.openai.com/v1/chat/completions");

            String userMessage = URLEncoder.encode(prompt, "UTF-8");
            String jsonPayload = "{\"messages\": [{\"role\": \"system\", \"content\": \"You are a helpful Minecraft assistant.\"}, {\"role\": \"user\", \"content\": \"" + userMessage + "\"}], \"model\": \"gpt-3.5-turbo\"}";
            StringEntity params = new StringEntity(jsonPayload);

            request.addHeader("Authorization", "Bearer " + API_KEY);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseText = EntityUtils.toString(entity);

            JsonParser parser = new JsonParser();
            JsonObject jsonResponse = parser.parse(responseText).getAsJsonObject();
            JsonArray choices = jsonResponse.getAsJsonArray("choices");
            JsonObject assistantMessage = choices.get(0).getAsJsonObject().getAsJsonObject("message");
            String assistantResponse = assistantMessage.get("content").getAsString();

            return assistantResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while getting ChatGPT response.";
        }

    }
}

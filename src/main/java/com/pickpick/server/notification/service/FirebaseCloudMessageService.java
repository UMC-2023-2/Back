package com.pickpick.server.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.google.gson.JsonParseException;
import com.pickpick.server.notification.dto.FcmMessage;
import java.io.FileInputStream;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/picpick-1947c/messages:send";

    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, String title, String body) throws IOException{
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json;charset=utf-8"));

        Request request = new Request.Builder()
            .url(API_URL)
            .post(requestBody)
            .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
            .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
            .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }
    private String makeMessage(String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
            .message(FcmMessage.Message.builder()
                .token(targetToken)
                .notification(FcmMessage.Notification.builder()
                    .title(title)
                    .body(body)
                    .image(null)
                    .build()
                ).build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException{
        String firebaseConfigPath = "/src/main/resources/picpick-1947c-firebase-adminsdk-oa23k-d93284724e.json";
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new FileInputStream(firebaseConfigPath))
            .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}

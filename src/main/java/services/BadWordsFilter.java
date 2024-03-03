package services;

import okhttp3.*;

import java.io.IOException;

public class BadWordsFilter {

    private static final OkHttpClient httpClient = new OkHttpClient();

    public static boolean containsBadWord(String content) {
        // Construct the request to the API endpoint
        Request request = new Request.Builder()
                .url("https://www.purgomalum.com/service/containsprofanity?text=" + content)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected HTTP code: " + response.code());
            }
            // Parse the API response and check if content contains bad words
            String responseBody = response.body().string();
            return Boolean.parseBoolean(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle API request error
            return true; // Consider content as containing bad words in case of error
        }
    }
}

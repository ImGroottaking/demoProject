package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ImageScraper {
    public static void main(String[] args) {
        String baseUrl = "https://pic.netbian.com";
        String targetUrl = "https://pic.netbian.com/4kmeinv/index_4.html";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(targetUrl))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            List<String> imageUrls = ImageUrlParser.parse(response.body());

            for (String imageUrl : imageUrls) {
                downloadImage(baseUrl + imageUrl);
            }

            System.out.println("图片下载成功！");
        } catch (IOException | InterruptedException e) {
            System.out.println("图片下载失败：" + e.getMessage());
        }
    }

    public static void downloadImage(String imageUrl) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(imageUrl))
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        Path savePath = Path.of("D:/beauty/" + fileName);

        Files.write(savePath, response.body());
    }
}

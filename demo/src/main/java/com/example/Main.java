package com.example;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author : ZX
 * @create : ${YEAR}-${MONTH}-${DAY} ${TIME}
 * @description :
 */
public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        String websiteUrl = "https://pic.netbian.com/tupian/"; // 网站的 URL
        String saveDirectory = "D:\\beauty\\"; // 图片保存目录


        //创建一个新的HttpClient
        HttpClient client = HttpClient.newHttpClient();

        for (int i = 1; i <= 10; i++) {// 先获取10个
            //根据网站图片的规律
            HttpRequest request = HttpRequest.newBuilder().
                    uri(new URI(websiteUrl + (30759 + i) + ".html"))
                    .build();
            //发送请求 这里我们选择ofString直接吧响应实体转换为String字符串
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            InputStream inputStream = response.body();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("GBK")));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            //页面图片
            String html = content.toString();

            //先找好我们要截取的前面的一段，作为前缀去匹配位置
            String prefix = "<a href=\"\" id=\"img\"><img src=\"";
            //再找好我们要截取的屁股后面紧接的位置，作为后缀去匹配位置
            String suffix = "\" data-pic=";

            //截取图片地址
            html = html.substring(html.indexOf(prefix) + prefix.length());
            html = html.substring(0, html.indexOf(suffix));



            //创建请求把图片取到
            HttpRequest imageRequest = HttpRequest.newBuilder().uri(new URI("https://pic.netbian.com" + html)).build();
            //这里以输入流的方式获取
            HttpResponse<InputStream> imageResponse = client.send(imageRequest, HttpResponse.BodyHandlers.ofInputStream());

            //拿到输入流和文件输出流
            InputStream imageInput = imageResponse.body();

            FileOutputStream stream = new FileOutputStream(saveDirectory+i+".jpg");
            int size;
            byte[] data = new byte[1024];
            while ((size=imageInput.read(data))>0){
                stream.write(data, 0, size);
            }
            System.out.println("下载成功"+imageRequest);


        }


    }
}
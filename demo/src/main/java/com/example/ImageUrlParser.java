package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : ZX
 * @create : 2023-05-27 2:45
 * @description :
 */
public class ImageUrlParser {


    public static List<String> parse(String html) {
        List<String> imageUrls = new ArrayList<>();

        // 使用正则表达式匹配 img 标签中的 src 属性
        String pattern = "<img[^>]+src\\s*=\\s*\"([^\">]+)\"";
        Pattern imgPattern = Pattern.compile(pattern);
        Matcher matcher = imgPattern.matcher(html);

        // 提取图片链接
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }
}

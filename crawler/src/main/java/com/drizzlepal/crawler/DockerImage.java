package com.drizzlepal.crawler;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/**
 * 爬取最新的可用的Docker镜像
 */
public class DockerImage implements Crawler<String> {

    public static void main(String[] args) throws Exception {
        System.out.println(new DockerImage().crawl());
    }

    @Override
    public String crawl() throws Exception {
        String html = "";
        Set<String> images = new HashSet<>();
        // 使用 httpclient 请求docker镜像分享获取html内容
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("https://www.coderjia.cn/archives/dba3f94c-a021-468a-8ac6-e840f85867ea");
            // 执行请求
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                // 获取响应的实体
                HttpEntity entity = response.getEntity();
                // 打印响应内容
                html = EntityUtils.toString(entity, "UTF-8");
                // 确保响应实体被正确释放
                EntityUtils.consume(entity);
            }
        }
        // 使用 jsoup 解析html内容
        Jsoup.parse(html).select("#lightGallery > table > tbody > tr").forEach(tr -> {
            Elements item = tr.select("td");
            if ("正常".equals(item.get(1).text())) {
                images.add("\"https://" + item.get(0).select("code").text() + "\"");
            }
        });
        // 输出镜像内容

        StringJoiner stringJoiner = new StringJoiner(",\n",
                "                sudo tee /etc/docker/daemon.json <<-'EOF'\n" + //
                        "                {\n" + //
                        "                    \"registry-mirrors\": [\n" + //
                        "                        ",
                "\n" + //
                        "\n" + //
                        "                                ]\n" + //
                        "                            }\n" + //
                        "\n" + //
                        "                        EOF");
        images.forEach(image -> {
            stringJoiner.add("        " + image);
        });
        StringBuilder res = new StringBuilder("");
        res.append("# docker 镜像更新请执行以下命令：\n");
        res.append(stringJoiner.toString());
        res.append("# 重启docker服务\n" + //
                "sudo systemctl daemon-reload && sudo systemctl restart docker && sudo docker info");
        return res.toString();
    }

}

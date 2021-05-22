package com.dcinside.cralwer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DcInsideCrawler implements Runnable{
    private final String mainUrl = "https://gall.dcinside.com";
    private final String boardUrl;

    public DcInsideCrawler(String targetUrl) {
        if (!targetUrl.startsWith(mainUrl)) {
            throw new IllegalArgumentException("This url is not dcinside");
        }

        boardUrl = targetUrl.substring(mainUrl.length());
    }



    public void crawling() {
        ImageDownloader imageDownloader = new ImageDownloader();
        List<String> contents = getContents(20);

        for (String content : contents) {
            try {
                Thread.sleep(1000);
                imageDownloader.downLoadImage(mainUrl + content);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    private List<String> getContents(int pageLimit) {
        List<String> contents = new ArrayList<>();

        for (int i = 0; i < pageLimit; i++) {

            try {
                Thread.sleep(500);
                Document boardDocument = Jsoup.connect(mainUrl + boardUrl + "&page=" + i).get();

                contents.addAll(
                        boardDocument.select("tr[class='ub-content us-post']")
                        .stream().filter(element -> !element.attributes().toString().contains("txt"))
                        .map(element -> element.select("a").attr("href"))
                        .collect(Collectors.toList())
                );

            } catch (IOException | InterruptedException exception) {
                exception.printStackTrace();
            }
        }

        return contents;
    }

    @Override
    public void run() {
        crawling();

    }
}

package com.dcinside.cralwer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DcInsideCrawler {
    private String mainUrl = "https://gall.dcinside.com";
    private String bestContentsBoardUrl = "/mgallery/board/lists?id=beautifulbody&exception_mode=recommend";
    private String contentUrl = "https://gall.dcinside.com/mgallery/board/view/?id=beautifulbody&no=";
    private String imageUrlStart = "dcimg2.dcinside.com/viewimage";

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
                Document boardDocument = Jsoup.connect(mainUrl + bestContentsBoardUrl + "&page=" + i).get();

                contents.addAll(boardDocument.select("tr[class='ub-content us-post']")
                        .stream().map(element -> element.select("a").attr("href"))
                        .collect(Collectors.toList()));

            } catch (IOException | InterruptedException exception) {
                exception.printStackTrace();
            }
        }

        return contents;
    }

    public static void main(String[] args) {
        DcInsideCrawler dcInsideCrawler = new DcInsideCrawler();
        dcInsideCrawler.crawling();
    }
}

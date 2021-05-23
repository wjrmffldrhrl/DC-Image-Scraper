package com.dcinside.cralwer.uploader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class ContentsLoader extends UpLoader {
    private final String mainUrl = "https://gall.dcinside.com";
    private final Queue<String> contentsQueue;
    private final String url;

    public ContentsLoader(Queue<String> contentsQueue, String url) {
        this.contentsQueue = contentsQueue;
        this.url = url;
    }

    void upload() {
        int page = 1;
        System.out.println("upload contents list : " + this.url);
        while(true){
            try {
                Thread.sleep(500);
                Document boardDocument = Jsoup.connect(url + "&page=" + (page++) + "&list_num=100").get();
                List<String> tmpContents = boardDocument.select("tr[class='ub-content us-post']").stream()
                        .filter(element -> !element.attributes().toString().contains("txt"))
                        .map(element -> mainUrl + element.select("a").attr("href"))
                        .collect(Collectors.toList());

                if (tmpContents.isEmpty()) {
                    System.out.println("last Page " + (page - 1));
                    break;
                }

                contentsQueue.addAll(tmpContents);
            } catch (IOException | InterruptedException exception) {
                exception.printStackTrace();
                break;
            }
        }

    }
}

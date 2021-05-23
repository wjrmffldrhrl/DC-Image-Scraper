package com.dcinside.cralwer;

import com.dcinside.cralwer.downloader.Downloader;
import com.dcinside.cralwer.downloader.ImageDownloader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DcInsideCrawler implements Runnable {
    private final String mainUrl = "https://gall.dcinside.com";
    private final String boardUrl;
    private final Downloader downloader;

    public DcInsideCrawler(String targetUrl, Downloader downloader) {
        if (!targetUrl.startsWith(mainUrl)) {
            throw new IllegalArgumentException("This url is not dcinside : [" + targetUrl + "]");
        }

        boardUrl = targetUrl.substring(mainUrl.length());
        this.downloader = downloader;
    }

    public void crawling() {
//        TODO page limit setting
        List<String> contents = getContents(999999);

        for (String content : contents) {
            try {
                Thread.sleep(1000);
                downloader.download(mainUrl + content);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * TODO page limit set
     *
     * @param pageLimit page limit
     * @return contents
     */
    private List<String> getContents(int pageLimit) {
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < pageLimit; i++) {

            try {
                Thread.sleep(500);
                Document boardDocument = Jsoup.connect(mainUrl + boardUrl + "&page=" + i + "&list_num=100").get();
                List<String> tmpContents = boardDocument.select("tr[class='ub-content us-post']").stream()
//                                .filter(element -> !element.attributes().toString().contains("txt"))
                                .map(element -> element.select("a").attr("href"))
                                .collect(Collectors.toList());

                System.out.println("contents size " + tmpContents.size());

                contents.addAll(tmpContents);

                if (tmpContents.size() < 95) {
                    System.out.println("last Page");
                    break;
                }
            } catch (IOException | InterruptedException exception) {
                exception.printStackTrace();
                break;
            }
        }

        return contents;
    }

    @Override
    public void run() {
        crawling();

    }
}

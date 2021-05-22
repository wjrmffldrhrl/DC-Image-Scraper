package com.dcinside.cralwer.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GallerySearcher {
    private static final String searchUrl = "https://gall.dcinside.com/ajax/gallery_main_ajax/search_gallmain";
    private static final String host = "gall.dcinside.com";


    public Map<String, String> search(String keyWord, boolean isMinor) {
        Map<String, String> galleries = new HashMap<>();
        String requestBody = getRequestBody(keyWord, isMinor);

        try {
            Document searchResult = Jsoup.connect(searchUrl)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Host", host)
                    .header("Content-Length", "")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .requestBody(requestBody)
                    .post();

            galleries = searchResult.selectFirst("ul[style='display:block']").select("a")
                    .stream().collect(Collectors.toMap(
                            e -> e.attr("href")
                                    .replace("javascript:page_move(\"8\",\"", "")
                                    .replace("\");", ""), Element::text));


        } catch (IOException e) {
            e.printStackTrace();
        }

        return galleries;
    }

    private String getRequestBody(String keyWord, boolean isMinor) {
        return "ci_t=7ab4eb8a2c2f0850fdeaa7f5bd78cb5e" +
                "&key=" + keyWord +
                "&galltype" + (isMinor ? 'M' : '1');
    }


    public static void main(String[] args) {
        GallerySearcher gallerySearcher = new GallerySearcher();

        Map<String, String> g = gallerySearcher.search("비", true);

        for (String galleryName : g.keySet()) {
            System.out.println(galleryName + " " + g.get(galleryName));
        }

    }
}

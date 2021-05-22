package com.dcinside.cralwer.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
                    .header("Content-Length", String.valueOf(requestBody.length()))
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
        try {
            return "ci_t=4c51301d4631d779daf7ca0c1302ff8b" +
                    "&key=" + URLEncoder.encode(keyWord, "UTF-8") +
                    "&galltype=" + (isMinor ? 'M' : '1');
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }


    public static void main(String[] args) {
        GallerySearcher gallerySearcher = new GallerySearcher();

        Map<String, String> g = gallerySearcher.search("비트", true);

        for (String galleryName : g.keySet()) {
            System.out.println(galleryName + " " + g.get(galleryName));
        }

    }
}

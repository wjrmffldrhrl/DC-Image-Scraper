package com.dcinside.cralwer;

import com.dcinside.cralwer.downloader.Downloader;
import com.dcinside.cralwer.downloader.ImageDownloader;
import com.dcinside.cralwer.uploader.ContentsLoader;
import com.dcinside.cralwer.uploader.UpLoader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class DcInsideCrawler implements Runnable {
    private final UpLoader upLoader;
    private final Downloader downloader;

    public DcInsideCrawler(UpLoader upLoader, Downloader downloader) {
        this.upLoader = upLoader;
        this.downloader = downloader;
    }

    public void crawling() {
        new Thread(upLoader).start();
        new Thread(downloader).start();
    }

    @Override
    public void run() {
        crawling();
    }
}

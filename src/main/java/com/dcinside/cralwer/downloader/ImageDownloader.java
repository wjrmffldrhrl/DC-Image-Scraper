package com.dcinside.cralwer.downloader;

import org.jsoup.Jsoup;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class ImageDownloader extends Downloader {

    private final String downloadPath = "img/";
    private final String imageUrlStart = "dcimg2.dcinside.com/viewimage";
    private final Queue<String> contentsQueue;

    public ImageDownloader(Queue<String> contentsQueue) {
        this.contentsQueue = contentsQueue;
    }

    @Override
    public void download() {

        int waitCount = 0;

        while (true) {
            try {
                if (contentsQueue.isEmpty()) {

                    Thread.sleep(5000);
                    if (waitCount > 6) {
                        break;
                    } else {
                        waitCount++;
                    }
                    continue;
                }

                waitCount = 0;
                String url = contentsQueue.poll();
                List<String> downloadUrls = Jsoup.connect(url).get().getElementsByAttributeValue("class", "appending_file_box")
                        .select("a").stream().map(element -> element.attr("href"))
                        .map(imageUrl -> imageUrl.replace("image.dcinside.com/download", imageUrlStart))
                        .filter(downloadUrl -> !downloadUrl.contains("javascript:"))
                        .collect(Collectors.toList());

                for (String downloadUrl : downloadUrls) {
                    Thread.sleep(1000);
                    createImage(downloadUrl, url);
                }

            } catch (IOException | InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void createImage(String downloadUrl, String contentUrl) {
        checkDownloadDirectory();
        String fileName = downloadUrl.split("&f_no=")[1];

        try (FileOutputStream outputStream = new FileOutputStream(downloadPath + fileName)) {
            URLConnection url = new URL(downloadUrl).openConnection();
            url.setRequestProperty("Referer", contentUrl);
            InputStream inputStream = new BufferedInputStream(url.getInputStream());

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while (true) {
                bytesRead = inputStream.read(dataBuffer, 0, 1024);

                if (bytesRead == -1) {
                    break;
                }
                outputStream.write(dataBuffer, 0, bytesRead);
            }


        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void checkDownloadDirectory() {
        File downloadDirectory = new File(downloadPath);

        if (!downloadDirectory.exists()) {
            System.out.println("No directory");
            System.out.println("Create directory " + downloadDirectory.mkdirs());
        }

    }
}

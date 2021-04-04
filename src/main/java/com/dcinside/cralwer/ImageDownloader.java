package com.dcinside.cralwer;

import org.jsoup.Jsoup;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

public class ImageDownloader {

    private String downloadPath = "img/";
    private String imageUrlStart = "dcimg2.dcinside.com/viewimage";


    public void downLoadImage(String imageUri) {

        try {
            List<String> downloadUrls;
            downloadUrls = Jsoup.connect(imageUri).get().getElementsByAttributeValue("class", "appending_file_box")
                    .select("a").stream().map(element -> element.attr("href"))
                    .map(imageUrl -> imageUrl.replace("image.dcinside.com/download", imageUrlStart))
                    .filter(downloadUrl -> !downloadUrl.contains("javascript:"))
                    .collect(Collectors.toList());
//                    .forEach(System.out::println);

            for (String downloadUrl : downloadUrls) {
                Thread.sleep(1000);
                createImage(downloadUrl, imageUri);
            }

        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }



    }


    private void createImage(String downloadUrl, String contentUrl) {
        String fileName = downloadUrl.split("&f_no=")[1];

        try (FileOutputStream outputStream = new FileOutputStream(downloadPath + fileName)){
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

}

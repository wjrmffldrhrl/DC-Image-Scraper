package com.dcinside.swing;


import com.dcinside.cralwer.DcInsideCrawler;
import com.dcinside.cralwer.search.GallerySearcher;

import javax.swing.*;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App extends JFrame {
    private static final String minorGalleryUrl = "https://gall.dcinside.com/mgallery/board/lists/?id=";
    private static final String mainGalleryUrl = "https://gall.dcinside.com/board/lists?id=";

    private static String targetUrl = "";
    private static JComboBox crawlingListBox = new JComboBox();
    private static final String bestBoardUrl = "&exception_mode=recommend";

    private static final GallerySearcher gallerySearcher = new GallerySearcher();

    public App() {
        super("DC Crawler");
        Container container = getContentPane();
        JButton startButton = new JButton("Start Crawling");
        JButton searchButton = new JButton("Search");

        JCheckBox bestBoardCheckBox = new JCheckBox();
        JCheckBox minorGalleryCheckBox = new JCheckBox();

        JTextArea searchTextBox = new JTextArea();
        bestBoardCheckBox.setText("개념글 크롤링하기");
        minorGalleryCheckBox.setText("마이너 갤러리 검색");

        startButton.addActionListener(e -> crawling(targetUrl, bestBoardCheckBox));
        crawlingListBox.addActionListener(e -> {
            targetUrl = crawlingListBox.getSelectedItem().toString().split(" - ")[1];
            System.out.println(crawlingListBox.getSelectedItem().toString());
        });
        searchButton.addActionListener(e -> searchCrawlingList(searchTextBox.getText().trim(), minorGalleryCheckBox.isSelected()));


        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(bestBoardCheckBox);
        container.add(crawlingListBox);
        container.add(startButton);
        container.add(searchTextBox);
        container.add(minorGalleryCheckBox);
        container.add(searchButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 300);
        setVisible(true);
    }

    private static void crawling(String url, JCheckBox bestBoardCheckBox) {
        if (bestBoardCheckBox.isSelected()) {
            url += bestBoardUrl;
        }
        System.out.println("crawling " + url);


        new Thread(new DcInsideCrawler(url)).start();
    }

    private static void searchCrawlingList(String keyword, boolean isMinor) {
        if (keyword.isEmpty()) {
            System.out.println("keyword is empty");
            return;
        }
        Map<String, String> galleries = gallerySearcher.search(keyword, isMinor);
        crawlingListBox.removeAllItems();
        for (String galleryName : galleries.keySet()) {

            String url = isMinor ? minorGalleryUrl + galleryName : mainGalleryUrl + galleryName;

            crawlingListBox.addItem(galleries.get(galleryName) + " - " + url);
            System.out.println(galleries.get(galleryName) + " - " + url);
        }

    }


    public static void main(String[] args) {
        new App();
    }
}

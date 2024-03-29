package com.dcinside.swing;


import com.dcinside.cralwer.DcInsideCrawler;
import com.dcinside.cralwer.downloader.ImageDownloader;
import com.dcinside.cralwer.search.GallerySearcher;
import com.dcinside.cralwer.uploader.ContentsLoader;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class App extends JFrame {
    private static final String minorGalleryUrl = "https://gall.dcinside.com/mgallery/board/lists/?id=";
    private static final String mainGalleryUrl = "https://gall.dcinside.com/board/lists?id=";

    private static String targetUrl = "";
    private static JComboBox crawlingListBox = new JComboBox();
    private static final String bestBoardUrl = "&exception_mode=recommend";

    private static final GallerySearcher gallerySearcher = new GallerySearcher();

    private final JPanel crawlingListPanel = new JPanel(new FlowLayout());

    private final Container container = getContentPane();

    public App() {
        super("DC Crawler");

        JPanel crawlingButtonPanel = new JPanel(new FlowLayout());
        JPanel searchPanel = new JPanel(new FlowLayout());

        JButton startButton = new JButton("Start Crawling");
        JButton searchButton = new JButton("Search");

        JCheckBox bestBoardCheckBox = new JCheckBox();
        JCheckBox minorGalleryCheckBox = new JCheckBox();

        JTextArea searchTextBox = new JTextArea(1, 50);

        bestBoardCheckBox.setText("개념글 크롤링하기");
        minorGalleryCheckBox.setText("마이너 갤러리 검색");

        startButton.addActionListener(e -> crawling(targetUrl, bestBoardCheckBox));
        searchButton.addActionListener(e -> searchCrawlingList(searchTextBox.getText().trim(), minorGalleryCheckBox.isSelected()));


        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        crawlingListPanel.add(crawlingListBox);
        crawlingButtonPanel.add(bestBoardCheckBox);
        crawlingButtonPanel.add(startButton);
        searchPanel.add(searchTextBox);
        searchPanel.add(searchButton);
        searchPanel.add(minorGalleryCheckBox);

        container.add(crawlingListPanel);
        container.add(crawlingButtonPanel);
        container.add(searchPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 300);
        setVisible(true);
    }

    private void crawling(String url, JCheckBox bestBoardCheckBox) {
        if (bestBoardCheckBox.isSelected()) {
            url += bestBoardUrl;
        }
        System.out.println("crawling " + url);

        final Queue<String> contentsQueue = new LinkedList<>();

        new Thread(new DcInsideCrawler(
                new ContentsLoader(contentsQueue, url),
                new ImageDownloader(contentsQueue)
        )).start();
    }

    private void searchCrawlingList(String keyword, boolean isMinor) {
        if (keyword.isEmpty()) {
            System.out.println("keyword is empty");
            return;
        }

        Map<String, String> galleries = gallerySearcher.search(keyword, isMinor);
        List<String> tmpGalleriesList = new ArrayList<>();

        for (String galleryName : galleries.keySet()) {
            String url = isMinor ? minorGalleryUrl + galleryName : mainGalleryUrl + galleryName;
            tmpGalleriesList.add(galleries.get(galleryName) + " - " + url);
        }

        if (tmpGalleriesList.isEmpty()) {
            System.out.println("No search result : " + keyword);
            return;
        }

        crawlingListPanel.remove(crawlingListBox);
        crawlingListBox = new JComboBox(tmpGalleriesList.toArray());

        crawlingListBox.addActionListener(e -> {
            targetUrl = crawlingListBox.getModel().getSelectedItem().toString().split(" - ")[1];
            System.out.println("target url : " + targetUrl);
        });
        crawlingListBox.setSelectedIndex(0);
        crawlingListPanel.add(crawlingListBox);

        container.revalidate();
    }


    public static void main(String[] args) {
        new App();
    }
}

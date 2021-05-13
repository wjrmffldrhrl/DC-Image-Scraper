package com.dcinside.swing;


import com.dcinside.cralwer.DcInsideCrawler;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private static String[] crawlingList = {"https://gall.dcinside.com/mgallery/board/lists/?id=beautifulbody",
            "https://gall.dcinside.com/board/lists?id=dcbest",
            "https://gall.dcinside.com/board/lists/?id=bitcoins"};
    private static String targetUrl = crawlingList[0];
    private static final JComboBox crawlingListBox = new JComboBox(crawlingList);
    private static final String bestBoardUrl = "&exception_mode=recommend";

    public App() {
        super("DC Crawler");
        Container container = getContentPane();
        JButton startButton = new JButton("Start");
        JCheckBox bestBoardCheckBox = new JCheckBox();
        bestBoardCheckBox.setText("개념글 크롤링하기");

        startButton.addActionListener(e -> crawling(targetUrl, bestBoardCheckBox));
        crawlingListBox.addActionListener(e -> {
            targetUrl = crawlingListBox.getSelectedItem().toString();
            System.out.println(crawlingListBox.getSelectedItem().toString());
        });


        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(bestBoardCheckBox);
        container.add(crawlingListBox);
        container.add(startButton);

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


    public static void main(String[] args) {
        new App();
    }
}

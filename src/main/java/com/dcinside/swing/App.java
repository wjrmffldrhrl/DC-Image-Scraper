package com.dcinside.swing;


import com.dcinside.cralwer.DcInsideCrawler;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    String[] crawlingList = {"https://gall.dcinside.com/mgallery/board/lists/?id=beautifulbody",
            "https://gall.dcinside.com/board/lists?id=dcbest"};
    String targetUrl = crawlingList[0];
    JComboBox crawlingListBox = new JComboBox(crawlingList);

    public App() {
        super("DC Crawler");
        Container container = getContentPane();
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> crawling(targetUrl));
        crawlingListBox.addActionListener(e -> {
            targetUrl = crawlingListBox.getSelectedItem().toString();
            System.out.println(crawlingListBox.getSelectedItem().toString());
        } );


        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(new JComboBox(crawlingList));
        container.add(startButton);
        setSize(700, 300);
        setVisible(true);
    }

    private static void crawling(String url) {
        System.out.println("crawling " + url);
        new DcInsideCrawler(url).crawling();

    }


    public static void main(String[] args) {
        new App();
    }
}

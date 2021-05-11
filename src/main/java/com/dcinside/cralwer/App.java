package com.dcinside.cralwer;


import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    String[] crawlingList = {"https://gall.dcinside.com/mgallery/board/lists/?id=beautifulbody"};

    public App() {
        super("DC Crwler");
        Container container = getContentPane();
        JButton startButton = new JButton("Start");

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(new JComboBox(crawlingList));
        container.add(startButton);
        setSize(400,500);
        setVisible(true);
    }



    public static void main(String[] args) {
        new App();
    }
}

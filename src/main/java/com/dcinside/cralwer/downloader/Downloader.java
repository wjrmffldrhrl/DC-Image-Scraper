package com.dcinside.cralwer.downloader;

import java.util.Queue;

public abstract class Downloader implements Runnable{

    @Override
    public void run() {
        download();
    }

    abstract void download();
}

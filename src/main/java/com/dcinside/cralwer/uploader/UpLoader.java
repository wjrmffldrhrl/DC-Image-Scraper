package com.dcinside.cralwer.uploader;

public abstract class UpLoader implements Runnable{

    @Override
    public void run() {
        upload();

    }

    abstract void upload();

}

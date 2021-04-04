package com.github.gallery.crawler

import com.github.gallery.crawler.file.ImageFileCreator

fun main(args: Array<String>) {
    println("hello")

    Thread(GalleryContentUrlCrawler(ImageFileCreator())).run()

}


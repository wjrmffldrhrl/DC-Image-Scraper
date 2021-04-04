package com.github.gallery.crawler.file

class ImageFileCreator : FileCreator {
    override fun createFileFromUrl(url: String): Boolean {
        println("create image")

        return true
    }
}
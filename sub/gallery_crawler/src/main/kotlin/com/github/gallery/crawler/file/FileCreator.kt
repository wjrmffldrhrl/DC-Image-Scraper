package com.github.gallery.crawler.file

interface FileCreator {
    fun createFileFromUrl(url: String): Boolean


}
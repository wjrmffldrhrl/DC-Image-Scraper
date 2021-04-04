package com.github.gallery.crawler

interface GalleryCrawler : Runnable {
    fun crawling(): Boolean

    override fun run() {
        var hasNextItem = true
        while (hasNextItem) {
            hasNextItem = crawling()
        }
    }

}
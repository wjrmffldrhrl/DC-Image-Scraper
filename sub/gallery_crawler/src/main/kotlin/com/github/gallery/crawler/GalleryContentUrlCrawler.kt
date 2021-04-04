package com.github.gallery.crawler

import com.github.gallery.crawler.file.FileCreator
import org.jsoup.Jsoup
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class GalleryContentUrlCrawler(private val fileCreator: FileCreator) : GalleryCrawler {

    private val mainUrl = "https://gall.dcinside.com"
    private val boardUrl = "/board/lists?id=baseball_new10"

    private val contentUrl = "https://gall.dcinside.com/board/view/?id=baseball_new10&no="
    private val imageUrlStart = "dcimg2.dcinside.com/viewimage"
    override fun crawling(): Boolean {
        val boardDocument = Jsoup.connect(mainUrl + boardUrl).get()
        var targetId = ""
        try {
            Thread.sleep(5000)
            targetId = boardDocument.select("tr[class='ub-content us-post']").filter { element ->
                element.attr("data-type").equals("icon_pic")
            }.map { element ->
                element.text().split(" ")[0]
            }.first()

        } catch (e: Exception) {
            println(e)
            Thread.sleep(100000)
            return true
        }


        Thread() {
            try {
                var index = 1
                Jsoup.connect(contentUrl + targetId).get().getElementsByAttributeValue("class", "appending_file_box")
                    .select("a").map { element -> element.attr("href") }
                    .map() { imageUrl ->
                        imageUrl.replace("image.dcinside.com/download", imageUrlStart)
                    }.forEach { imageUrl ->


                        val fileExtension = imageUrl.split(".").last()
                        val url = URL(imageUrl).openConnection()
                        url.setRequestProperty("Referer", contentUrl + targetId)

                        val inputStream = BufferedInputStream(url.getInputStream())
                        val outputStream =
                            FileOutputStream(File("img/$targetId" + "_" + (index++) + "." + fileExtension))

                        var databuffer = ByteArray(1024)
                        var bytesRead = 0
                        while (true) {
                            bytesRead = inputStream.read(databuffer, 0, 1024)
                            if (bytesRead == -1) {
                                break
                            }
                            outputStream.write(databuffer, 0, bytesRead)
                        }
                    }
            } catch (e: Exception) {
                println(e)
            }

        }.run()

        return true
    }

}
package com.my_pdf

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyPdfApplication

fun main(args: Array<String>) {
    val tesseractLibraryPath = "/opt/homebrew/Cellar/tesseract/5.5.0/lib"
    System.setProperty("jna.library.path", tesseractLibraryPath)
    runApplication<MyPdfApplication>(*args)
}

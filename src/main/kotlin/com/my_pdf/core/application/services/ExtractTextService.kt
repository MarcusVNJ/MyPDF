package com.my_pdf.core.application.services

import com.my_pdf.core.domain.Document
import com.my_pdf.core.domain.PageDocument
import java.awt.image.BufferedImage
import net.sourceforge.tess4j.ITesseract
import net.sourceforge.tess4j.Tesseract
import org.apache.pdfbox.multipdf.Splitter
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import org.springframework.stereotype.Service


@Service
class ExtractTextService() {
    fun execute(document: Document): List<PageDocument> {

        val tesseract: ITesseract = Tesseract()
        tesseract.setOcrEngineMode(2)
        tesseract.setVariable("tessedit_create_hocr", "0")
        tesseract.setLanguage("por+osd")
        tesseract.setDatapath("/opt/homebrew/Cellar/tesseract/5.5.0/share/tessdata")

        val documents = mutableListOf<PageDocument>()

        document.pddDocument.use { document ->
            val splitter = Splitter()
            val pages: List<PDDocument> = splitter.split(document)

            pages.forEach {page ->
                kotlin.runCatching {
                    val renderer = PDFRenderer(page)
                    val image: BufferedImage = renderer.renderImageWithDPI(0, 300f, ImageType.GRAY)
                    val pageText = tesseract.doOCR(image)
                    println(pageText)

                    documents.add(PageDocument(content = pageText, image = image))

                }.onFailure {
                    throw it
                }
            }
        }
        return documents
    }
}

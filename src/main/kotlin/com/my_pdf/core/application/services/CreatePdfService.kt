package com.my_pdf.core.application.services

import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.my_pdf.core.domain.PageDocument
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import org.springframework.stereotype.Service

@Service
class CreatePdfService {
    fun execute(pages: List<PageDocument>): ByteArrayOutputStream {
        val outputStream = ByteArrayOutputStream()
        val document = Document()
        val writer = PdfWriter.getInstance(document, outputStream)

        document.open()

        pages.forEach { page ->
            val imageBytes: ByteArray = ByteArrayOutputStream().use { baos ->
                ImageIO.write(page.image, "PNG", baos)
                baos.toByteArray()
            }
            val iTextImage = Image.getInstance(imageBytes)

            iTextImage.scaleToFit(PageSize.A4.width, PageSize.A4.height)
            document.add(iTextImage)
            document.add(Paragraph(page.content))
            document.newPage()
        }

        document.close()
        writer.close()
        return outputStream
    }
}

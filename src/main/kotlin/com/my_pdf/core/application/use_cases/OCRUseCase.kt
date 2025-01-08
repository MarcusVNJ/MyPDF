package com.my_pdf.core.application.use_cases

import com.my_pdf.core.application.services.CreatePdfService
import com.my_pdf.core.application.services.ExtractTextService
import com.my_pdf.core.domain.Document
import com.my_pdf.core.domain.PageDocument
import com.my_pdf.core.domain.fatories.DocumentFactory
import java.io.ByteArrayOutputStream


import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class OCRUseCase(
    private val extractText: ExtractTextService,
    private val createPdf: CreatePdfService,
    private val fatory: DocumentFactory
) {

    fun execute(file: MultipartFile): ByteArrayOutputStream {

        file.takeIf { it.originalFilename?.endsWith(".pdf", ignoreCase = true) ?: false }.run {
            val document: Document = fatory.create(file)
            val pages: List<PageDocument> = extractText.execute(document)
            val documentPdf: ByteArrayOutputStream = createPdf.execute(pages)
            return documentPdf
        }
        throw Exception() //message = "não foi possivel criar o documento PDF"
    }
}

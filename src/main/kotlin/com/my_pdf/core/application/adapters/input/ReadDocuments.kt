package com.my_pdf.core.application.adapters.input

import com.my_pdf.core.application.adapters.input.resources.PdfResource
import com.my_pdf.core.application.use_cases.OCRUseCase
import java.io.ByteArrayOutputStream
import org.springframework.http.HttpHeaders
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class ReadDocuments(private val ocrDocument: OCRUseCase) : PdfResource {

    @PostMapping(
        "/v1/ocr",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_PDF_VALUE]
    )
    fun execute(@RequestParam("file") document: MultipartFile): ResponseEntity<ByteArrayResource> {
        val documentPdf: ByteArrayOutputStream = ocrDocument.execute(document)

        return ResponseEntity.ok()
            .contentLength(documentPdf.size().toLong())
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${document.originalFilename}\"")
            .body(ByteArrayResource(documentPdf.toByteArray()))
    }
}

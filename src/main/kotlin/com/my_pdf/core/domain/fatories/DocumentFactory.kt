package com.my_pdf.core.domain.fatories

import com.my_pdf.core.domain.Document
import org.apache.pdfbox.Loader
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
object DocumentFactory : CreateDocumentFactory {
    override fun create(file: MultipartFile): Document {
        return Document(name = file.originalFilename!!, type = file.contentType!!, pddDocument = Loader.loadPDF(file.bytes)) //TODO Lembre de olhar estas verificações "!!"quer dizem que não está vazio estes valores, pode da problema no futuro
    }
}
package com.my_pdf.core.domain.fatories

import com.my_pdf.core.domain.Document
import org.springframework.web.multipart.MultipartFile

interface CreateDocumentFactory {
    fun create(file: MultipartFile): Document
}

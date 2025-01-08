package com.my_pdf.core.domain

import org.apache.pdfbox.pdmodel.PDDocument

data class Document(
    val type: String,
    val name: String,
    val pddDocument: PDDocument
)
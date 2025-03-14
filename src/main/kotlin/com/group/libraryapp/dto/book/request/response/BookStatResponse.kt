package com.group.libraryapp.dto.book.request.response

import com.group.libraryapp.domain.book.BookType

data class BookStatResponse(
    val type: BookType,
    var count: Int,
) {
    fun plusOne() {
        this.count++
    }

}
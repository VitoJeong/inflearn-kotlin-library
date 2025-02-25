package com.group.libraryapp.service.book;

import com.group.libraryapp.service.book.BookService
import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class JavaBookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {

    @AfterEach
    fun deleteAll() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("책 등록이 정상 동작한다.")
    fun saveBookTest() {

        // given
        val bookRequest = BookRequest("이상한 나라의 엘리스")

        // when
        bookService.saveBook(bookRequest)

        // then
        val books = bookRepository.findAll()
        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo(bookRequest.name)
    }

    @Test
    @DisplayName("책 대출이 정상 동작한다")
    fun loanBookTest() {

        // given
        bookRepository.save(Book("이상한 나라의 엘리스"))
        val savedUser = userRepository.save(User("정창화", 30))
        val request = BookLoanRequest(
            "정창화",
            "이상한 나라의 엘리스",
        )

        // when
        bookService.loanBook(request)

        // then
        val loans = userLoanHistoryRepository.findAll()
        assertThat(loans).hasSize(1)
        assertThat(loans[0].bookName).isEqualTo(request.bookName)
        assertThat(loans[0].user.name).isEqualTo(request.userName)
        assertThat(loans[0].user.id).isEqualTo(savedUser.id)
        assertThat(loans[0].isReturn).isEqualTo(false)
    }

    @Test
    @DisplayName("책이 진작 대출돼어 있다면, 신규 대출이 실패한다.")
    fun loanBookFailTest() {

        // given
        bookRepository.save(Book("이상한 나라의 엘리스"))
        val savedUser = userRepository.save(User("정창화", 30))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "이상한 나라의 엘리스", false))

        val request = BookLoanRequest(
            "정창화",
            "이상한 나라의 엘리스",
        )

        // when & then
        val message = assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.message
        assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")

    }

    @Test
    @DisplayName("책 반납이 정상 동작한다.")
    fun returnBookTest() {

        // given
        val savedUser = userRepository.save(User("정창화", 30))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "이상한 나라의 엘리스", false))
        val request = BookReturnRequest(savedUser.name, "이상한 나라의 엘리스")

        // when
        bookService.returnBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].isReturn).isEqualTo(true)

    }

}
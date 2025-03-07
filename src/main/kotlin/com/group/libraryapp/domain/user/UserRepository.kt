package com.group.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface UserRepository: JpaRepository<User, Long> {
    fun findByName(userName: String): User?

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN u.userLoanHistories")
    fun findAllWithHistories(): List<User>
}
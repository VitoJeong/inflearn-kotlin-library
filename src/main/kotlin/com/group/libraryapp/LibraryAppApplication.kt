package com.group.libraryapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LibraryAppApplication

// static 대신 top-level functions 으로 선언한다.
// (companion object 로도 선언할 수 있다.)
fun main(args: Array<String>) {
    runApplication<LibraryAppApplication>(*args)
}

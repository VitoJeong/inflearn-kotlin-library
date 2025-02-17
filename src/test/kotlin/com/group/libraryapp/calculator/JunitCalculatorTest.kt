package com.group.libraryapp.calculator

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class JunitCalculatorTest {

    @Test
    fun addTest() {

        // GIVEN
        val calculator = Calculator(5)

        // WHEN
        calculator.add(3)

        // THEN
        assertThat(calculator.number).isEqualTo(8)

    }

    @Test
    fun minusTest() {

        // GIVEN
        val calculator = Calculator(5)

        // WHEN
        calculator.minus(3)

        // THEN
        assertThat(calculator.number).isEqualTo(2)

    }

    @Test
    fun multiplyTest() {

        // GIVEN
        val calculator = Calculator(5)

        // WHEN
        calculator.multiply(3)

        // THEN
        assertThat(calculator.number).isEqualTo(15)

    }

    @Test
    fun divideTest() {

        // GIVEN
        val calculator = Calculator(5)

        // WHEN
        calculator.divide(2)

        // THEN
        assertThat(calculator.number).isEqualTo(2)

    }

    @Test
    fun divideExceptionTest() {

        // given
        val calculator = Calculator(5)

        // when & then
        val message = assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }.apply {
            assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")
        }

    }

}
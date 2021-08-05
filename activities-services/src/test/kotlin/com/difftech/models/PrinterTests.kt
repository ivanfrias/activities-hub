package com.difftech.models

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PrinterTests {
    @Test
    fun testPrintSomething() {
        val printer = Printer()
        val exp = "Something"
        Assertions.assertEquals(exp, printer.printSomething())
    }
}
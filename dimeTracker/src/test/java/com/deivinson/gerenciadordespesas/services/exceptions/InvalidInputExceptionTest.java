package com.deivinson.gerenciadordespesas.services.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class InvalidInputExceptionTest {

	@Test
    public void testInvalidInputException() {
        String errorMessage = "Entrada inválida.";

        InvalidInputException invalidInputException = new InvalidInputException(errorMessage);

        assertThat(invalidInputException.getMessage()).isEqualTo(errorMessage);

        assertThat(invalidInputException.getCause()).isNull();
    }
}

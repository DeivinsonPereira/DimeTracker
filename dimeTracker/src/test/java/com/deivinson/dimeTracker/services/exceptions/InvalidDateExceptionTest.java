package com.deivinson.dimeTracker.services.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class InvalidDateExceptionTest {

	@Test
    public void testDataInvalidaException() {
        String errorMessage = "Invalid Date.";

        InvalidDateException dataInvalidaException = new InvalidDateException(errorMessage);

        assertThat(dataInvalidaException.getMessage()).isEqualTo(errorMessage);

        assertThat(dataInvalidaException.getCause()).isNull();
    }
}

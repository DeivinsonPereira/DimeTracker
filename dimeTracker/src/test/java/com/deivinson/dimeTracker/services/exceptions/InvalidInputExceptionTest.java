package com.deivinson.dimeTracker.services.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class InvalidInputExceptionTest {

	@Test
    public void testInvalidInputException() {
        String errorMessage = "Invalid input.";

        InvalidInputException invalidInputException = new InvalidInputException(errorMessage);

        assertThat(invalidInputException.getMessage()).isEqualTo(errorMessage);

        assertThat(invalidInputException.getCause()).isNull();
    }
}

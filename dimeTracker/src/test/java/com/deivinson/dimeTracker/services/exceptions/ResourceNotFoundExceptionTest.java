package com.deivinson.dimeTracker.services.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ResourceNotFoundExceptionTest {

	@Test
    public void testResourceNotFoundException() {
        String errorMessage = "Resource not found.";

        ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException(errorMessage);

        assertThat(resourceNotFoundException.getMessage()).isEqualTo(errorMessage);

        assertThat(resourceNotFoundException.getCause()).isNull();
    }
}

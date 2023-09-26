package com.deivinson.dimeTracker.controller.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.deivinson.dimeTracker.services.exceptions.InvalidDateException;
import com.deivinson.dimeTracker.services.exceptions.DatabaseException;
import com.deivinson.dimeTracker.services.exceptions.InvalidInputException;
import com.deivinson.dimeTracker.services.exceptions.ResourceNotFoundException;

public class ResourceExceptionHandlerTest {
	
	private ResourceExceptionHandler resourceExceptionHandler;
    private HttpServletRequest request;

	@BeforeEach
    public void setUp() {
        resourceExceptionHandler = new ResourceExceptionHandler();
        request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/example/resource");
    }

    @Test
    public void testEntityNotFound() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");

        ResponseEntity<StandardError> response = resourceExceptionHandler.entityNotFound(exception, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getError()).isEqualTo("Resource not found");
        assertThat(response.getBody().getPath()).isEqualTo("/example/resource");
    }

    @Test
    public void testDatabase() {
        DatabaseException exception = new DatabaseException("Database exception");

        ResponseEntity<StandardError> response = resourceExceptionHandler.database(exception, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getError()).isEqualTo("Database exception");
        assertThat(response.getBody().getPath()).isEqualTo("/example/resource");
    }

    @Test
    public void testInvalidInputException() {
        InvalidInputException exception = new InvalidInputException("Invalid input exception");

        ResponseEntity<StandardError> response = resourceExceptionHandler.invalidInputException(exception, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getError()).isEqualTo("Invalid input exception");
        assertThat(response.getBody().getPath()).isEqualTo("/example/resource");
    }

    @Test
    public void testHandleInvalidInputException() {
        InvalidDateException exception = new InvalidDateException("Invalid data exception");

        ResponseEntity<StandardError> response = resourceExceptionHandler.handleInvalidInputException(exception, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getError()).isEqualTo("The start date cannot be greater than the end date");
        assertThat(response.getBody().getPath()).isEqualTo("/example/resource");
    }
}

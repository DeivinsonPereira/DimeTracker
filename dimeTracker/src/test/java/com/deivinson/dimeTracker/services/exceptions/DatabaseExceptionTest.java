package com.deivinson.dimeTracker.services.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DatabaseExceptionTest {
	
	@Test
    public void testDatabaseException() {
        String errorMessage = "Database error.";

        DatabaseException databaseException = new DatabaseException(errorMessage);

        assertThat(databaseException.getMessage()).isEqualTo(errorMessage);

        assertThat(databaseException.getCause()).isNull();
    }
}

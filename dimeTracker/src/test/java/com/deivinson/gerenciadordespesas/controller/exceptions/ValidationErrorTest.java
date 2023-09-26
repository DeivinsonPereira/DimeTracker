package com.deivinson.gerenciadordespesas.controller.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ValidationErrorTest {

	@Test
    public void testEmptyConstructor() {
        ValidationError validationError = new ValidationError();

        assertThat(validationError).isNotNull();

        assertThat(validationError.getErrors()).isEmpty();
    }

    @Test
    public void testGettersAndSetters() {
        ValidationError validationError = new ValidationError();

        validationError.setTimestamp(Instant.now());
        validationError.setStatus(HttpStatus.BAD_REQUEST.value());
        validationError.setError("Validation error");
        validationError.setMessage("Validation message");
        validationError.setPath("/example/path");

        assertThat(validationError.getTimestamp()).isNotNull();
        assertThat(validationError.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(validationError.getError()).isEqualTo("Validation error");
        assertThat(validationError.getMessage()).isEqualTo("Validation message");
        assertThat(validationError.getPath()).isEqualTo("/example/path");
    }

    @Test
    public void testAddError() {
        ValidationError validationError = new ValidationError();

        String fieldName = "exampleField";
        String message = "Example message";
        validationError.addError(fieldName, message);

        List<FieldMessage> errors = validationError.getErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getFieldName()).isEqualTo(fieldName);
        assertThat(errors.get(0).getMessage()).isEqualTo(message);
    }
}

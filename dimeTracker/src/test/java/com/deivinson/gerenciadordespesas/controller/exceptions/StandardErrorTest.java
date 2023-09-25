package com.deivinson.gerenciadordespesas.controller.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class StandardErrorTest {

	@Test
    public void testConstrutorVazio() {
        StandardError standardError = new StandardError();

        assertThat(standardError).isNotNull();

        assertThat(standardError.getTimestamp()).isNull();
        assertThat(standardError.getStatus()).isNull();
        assertThat(standardError.getError()).isNull();
        assertThat(standardError.getMessage()).isNull();
        assertThat(standardError.getPath()).isNull();
    }

    @Test
    public void testGettersEsetters() {
        StandardError standardError = new StandardError();

        Instant timestamp = Instant.now();
        Integer status = 400;
        String error = "Bad Request";
        String message = "Example message";
        String path = "/example/path";

        standardError.setTimestamp(timestamp);
        standardError.setStatus(status);
        standardError.setError(error);
        standardError.setMessage(message);
        standardError.setPath(path);

        assertThat(standardError.getTimestamp()).isEqualTo(timestamp);
        assertThat(standardError.getStatus()).isEqualTo(status);
        assertThat(standardError.getError()).isEqualTo(error);
        assertThat(standardError.getMessage()).isEqualTo(message);
        assertThat(standardError.getPath()).isEqualTo(path);
    }
}

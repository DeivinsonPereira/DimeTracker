package com.deivinson.gerenciadordespesas.services.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DataInvalidaExceptionTest {

	@Test
    public void testDataInvalidaException() {
        String errorMessage = "Data inválida.";

        DataInvalidaException dataInvalidaException = new DataInvalidaException(errorMessage);

        assertThat(dataInvalidaException.getMessage()).isEqualTo(errorMessage);

        assertThat(dataInvalidaException.getCause()).isNull();
    }
}

package com.deivinson.gerenciadordespesas.controller.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class FieldMessageTest {

	@Test
    public void testConstrutorVazio() {
        FieldMessage fieldMessage = new FieldMessage();

        assertThat(fieldMessage).isNotNull();

        assertThat(fieldMessage.getFieldName()).isNull();
        assertThat(fieldMessage.getMessage()).isNull();
    }
	
	@Test
    public void testConstrutorComArgumentos() {
        String fieldName = "exampleField";
        String message = "Example message";

        FieldMessage fieldMessage = new FieldMessage(fieldName, message);

        assertThat(fieldMessage.getFieldName()).isEqualTo(fieldName);
        assertThat(fieldMessage.getMessage()).isEqualTo(message);
    }

    @Test
    public void testGettersEsetters() {
        String fieldName = "exampleField";
        String message = "Example message";

        FieldMessage fieldMessage = new FieldMessage();

        fieldMessage.setFieldName(fieldName);
        fieldMessage.setMessage(message);

        assertThat(fieldMessage.getFieldName()).isEqualTo(fieldName);
        assertThat(fieldMessage.getMessage()).isEqualTo(message);
    }
}

package com.deivinson.gerenciadordespesas.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryTest {
	
	private Category category1;
	private Category category2;

	@BeforeEach
	public void setUp() {
		category1 = new Category(1L, "Nutrition");
        category2 = new Category(2L, "Transport");
	}
	
	@Test
	public void testGetAndSetId() {
		category1.setId(1L);
		assertEquals(1L, category1.getId());
	}
	
	@Test
	public void testGetAndSetName() {
		category1.setName("joe");
		
		assertTrue(category1.getName().equalsIgnoreCase("Joe"));
	}
	
    @Test
    public void testEqualsWithEqualInstances() {
        Category category = new Category(1L, "Nutrition");
        assertThat(category1.equals(category)).isTrue();
    }

    @Test
    public void testEqualsWithDifferentInstances() {
        assertThat(category1.equals(category2)).isFalse();
    }

    @Test
    public void testEqualsifNull() {
        assertThat(category1.equals(null)).isFalse();
    }

    @Test
    public void testEqualsReflexivity() {
        assertThat(category1.equals(category1)).isTrue();
    }

    @Test
    public void testEqualsSymmetry() {
        assertThat(category1.equals(category2)).isEqualTo(category2.equals(category1));
    }

    @Test
    public void testEqualsTransitivity() {
        Category category = new Category(1L, "Nutrition");
        assertThat(category1.equals(category)).isTrue();
        assertThat(category.equals(category2)).isFalse();
        assertThat(category1.equals(category2)).isFalse();
    }

    @Test
    public void testHashCodeConsistent() {
        Category category = new Category(1L, "Nutrition");
        assertThat(category1.hashCode()).isEqualTo(category.hashCode());
    }

    @Test
    public void testHashCodeDifferentForDifferentObjects() {
        assertThat(category1.hashCode()).isNotEqualTo(category2.hashCode());
    }

    @Test
    public void testToString() {
        assertThat(category1.toString()).isEqualTo("Category(id=1, name=Nutrition, expenses=[])");
    }
    
    @Test
    public void testEmptyConstructor() {
        Category category = new Category();

        assertThat(category).isNotNull();

        assertThat(category.getId()).isNull();
        assertThat(category.getName()).isNull();
        assertThat(category.getExpenses()).isNotNull().isEmpty();
    }
}

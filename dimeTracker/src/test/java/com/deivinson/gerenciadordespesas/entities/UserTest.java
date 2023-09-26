package com.deivinson.gerenciadordespesas.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deivinson.gerenciadordespesas.tests.Factory;

public class UserTest {

	private User user;
	
	@BeforeEach
	public void setUp() {
		user = Factory.emptyConstructorUser();;
	}
	
	@Test
	public void testGetAndSetId() {
		user.setId(1L);
		assertEquals(1L, user.getId());
	}
	
	@Test
	public void testGetAndSetName() {
		user.setName("joe");
		
		assertTrue(user.getName().equalsIgnoreCase("Joe"));
	}
	
	@Test
    public void testToString() {
        User user = new User(1L, "Joe");

        String expectedToString = "User(id=1, name=Joe, expenses=[])";
        assertThat(user.toString()).isEqualTo(expectedToString);
    }
	
	@Test
    public void testEqualsWithEqualInstances() {
        User user1 = new User(1L, "Joe");
        User user2 = new User(1L, "Joe");

        assertThat(user1).isEqualTo(user2);
    }

    @Test
    public void testEqualsWithNotEqualInstances() {
        User user1 = new User(1L, "Joe");
        User user2 = new User(2L, "Mary");

        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    public void testHashCodeConsistent() {
        User user1 = new User(1L, "Joe");
        User user2 = new User(1L, "Joe");

        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }

    @Test
    public void testHashCodeDifferentForDifferentObjects() {
        User user1 = new User(1L, "Joe");
        User user2 = new User(2L, "Mary");

        assertThat(user1.hashCode()).isNotEqualTo(user2.hashCode());
    }
    
    @Test
    public void testConstructorWithArgs() {
        User user = new User(1L, "Joe");

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("Joe");
    }
    
    @Test
    public void testEqualsIsNotNull() {
        User user = new User(1L, "Joe");

        assertThat(user.equals(null)).isFalse();
    }
    
    @SuppressWarnings("unlikely-arg-type")
	@Test
    public void testEqualsWithObjectsFromDifferentClasses() {
        User user = new User(1L, "Joe");
        Category category = new Category();

        assertThat(user.equals(category)).isFalse();
    }
    
    @Test
    public void testEqualsWithDifferentInstancesIsFalse() {
        User user1 = new User(1L, "Joe");
        User user2 = new User(2L, "Mary");

        assertThat(user1.equals(user2)).isFalse();
    }
    
}

package com.deivinson.dimeTracker.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.deivinson.dimeTracker.entities.Category;
import com.deivinson.dimeTracker.entities.Expense;
import com.deivinson.dimeTracker.entities.User;
import com.deivinson.dimeTracker.tests.Factory;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long contTotalUsers;
	
	@BeforeEach
	void setUp() {
		
		existingId = 1L;
		nonExistingId = 999L;
		contTotalUsers = 1L;
	}
	
	@Test
	public void testSaveUser() {
		
		User user = Factory.emptyConstructorUser();
		user.setId(existingId);
		user.setName("test");
		
		userRepository.save(user);
		
		User userSaved = userRepository.findById(user.getId()).orElse(null);
		
		assertNotNull(userSaved);
		assertEquals(existingId, userSaved.getId());
		assertEquals(user, userSaved);
		assertTrue(userSaved.getName().equalsIgnoreCase("test"));
	}
	
	@Test
	public void testFindUserById() {
		
		User user = Factory.emptyConstructorUser();
		user.setId(existingId);
		user.setName("test");
		
		userRepository.save(user);
		
		User userFound = userRepository.findById(user.getId()).orElse(null);
		
		assertNotNull(userFound);
		assertEquals(existingId, userFound.getId());
		assertEquals("test", userFound.getName());
	}
	
	@Test
	public void testFindUserByIdNotFound() {
		
		User userFound = userRepository.findById(nonExistingId).orElse(null);
		
		assertNull(userFound);
	}
	
	@Test
	public void testFindAllUser() {
		
		User user1 = Factory.emptyConstructorUser();
		user1.setName("User 1");
		userRepository.save(user1);
		
		User user2 = Factory.emptyConstructorUser();
		user2.setName("User 2");
		userRepository.save(user2);
		
		User user3 = Factory.emptyConstructorUser();
		user3.setName("User 3");
		userRepository.save(user3);
		
        List<User> allUsers = userRepository.findAll();
        
        assertFalse(allUsers.isEmpty());
        assertEquals(contTotalUsers + 3, allUsers.size());
        
        assertTrue(allUsers.stream().anyMatch(c -> c.getName().equals("User 1")));
        assertTrue(allUsers.stream().anyMatch(c -> c.getName().equals("User 2")));
        assertTrue(allUsers.stream().anyMatch(c -> c.getName().equals("User 3")));
        
	}
	
	@Test
	public void testUpdateUser(){
		
		User user = userRepository.findById(1L).orElse(null);
		
		user.setId(1L);
		user.setName("Joe");
		
		userRepository.save(user);
		
		assertEquals(1L, user.getId());
		assertTrue(user.getName().equalsIgnoreCase("Joe"));
		
		user.setId(35L);
		user.setName("Natasha");
		
		userRepository.save(user);
		assertNotEquals(1L, user.getId());
		assertFalse(user.getName().equalsIgnoreCase("Joe"));
		assertEquals(35L, user.getId());
		assertTrue(user.getName().equalsIgnoreCase("Natasha"));
		
	}
	
	@Test
	public void deleteUser() {
		Category category = Factory.constructorCategoryWihArgs();
		
		assertEquals(1L, category.getId());
		assertTrue(category.getName().equalsIgnoreCase("Energy"));
		
		userRepository.deleteById(1L);
		
		Optional<User> result = userRepository.findById(existingId);
		
		assertFalse(result.isPresent());
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

		User user = Factory.emptyConstructorUser();
		user.setId(null);
		
		user = userRepository.save(user);
		
		assertNotNull(user);
		assertEquals(contTotalUsers + 1L, user.getId());
	}
	
	@Test
	public void OneToManyRelationshipCategoryForExpense () {
		User user = Factory.constructorUserWithArgsWithExpense();
		
		userRepository.save(user);
		
		User userRelationship = userRepository.findById(user.getId()).orElse(null); 
		assertNotNull(userRelationship);
		assertEquals(1, userRelationship.getExpenses().size());
		
		for(Expense expense : user.getExpenses()) {
			assertEquals(userRelationship, expense.getUser());
		}
	}
}

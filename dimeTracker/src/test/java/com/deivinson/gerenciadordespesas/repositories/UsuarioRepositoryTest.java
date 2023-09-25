package com.deivinson.gerenciadordespesas.repositories;

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

import com.deivinson.gerenciadordespesas.entities.Categoria;
import com.deivinson.gerenciadordespesas.entities.Despesa;
import com.deivinson.gerenciadordespesas.entities.Usuario;
import com.deivinson.gerenciadordespesas.tests.Factory;

@DataJpaTest
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long contTotalUsuarios;
	
	@BeforeEach
	void setUp() {
		
		existingId = 1L;
		nonExistingId = 999L;
		contTotalUsuarios = 1L;
	}
	
	@Test
	public void testSaveUsuario() {
		
		Usuario usuario = Factory.construtorUsuarioVazio();
		usuario.setId(existingId);
		usuario.setNome("Teste");
		
		usuarioRepository.save(usuario);
		
		Usuario usuarioSalva = usuarioRepository.findById(usuario.getId()).orElse(null);
		
		assertNotNull(usuarioSalva);
		assertEquals(existingId, usuarioSalva.getId());
		assertEquals(usuario, usuarioSalva);
		assertTrue(usuarioSalva.getNome().equalsIgnoreCase("Teste"));
	}
	
	@Test
	public void testFindUsuarioById() {
		
		Usuario usuario = Factory.construtorUsuarioVazio();
		usuario.setId(existingId);
		usuario.setNome("Teste");
		
		usuarioRepository.save(usuario);
		
		Usuario usuarioEncontrada = usuarioRepository.findById(usuario.getId()).orElse(null);
		
		assertNotNull(usuarioEncontrada);
		assertEquals(existingId, usuarioEncontrada.getId());
		assertEquals("Teste", usuarioEncontrada.getNome());
	}
	
	@Test
	public void testFindUsuarioByIdNotFound() {
		
		Usuario usuarioEncontrada = usuarioRepository.findById(nonExistingId).orElse(null);
		
		assertNull(usuarioEncontrada);
	}
	
	@Test
	public void testFindAllUsuario() {
		
		Usuario usuario1 = Factory.construtorUsuarioVazio();
		usuario1.setNome("Usuario 1");
		usuarioRepository.save(usuario1);
		
		Usuario usuario2 = Factory.construtorUsuarioVazio();
		usuario2.setNome("Usuario 2");
		usuarioRepository.save(usuario2);
		
		Usuario usuario3 = Factory.construtorUsuarioVazio();
		usuario3.setNome("Usuario 3");
		usuarioRepository.save(usuario3);
		
        List<Usuario> todosUsuarios = usuarioRepository.findAll();
        
        assertFalse(todosUsuarios.isEmpty());
        assertEquals(contTotalUsuarios + 3, todosUsuarios.size());
        
        assertTrue(todosUsuarios.stream().anyMatch(c -> c.getNome().equals("Usuario 1")));
        assertTrue(todosUsuarios.stream().anyMatch(c -> c.getNome().equals("Usuario 2")));
        assertTrue(todosUsuarios.stream().anyMatch(c -> c.getNome().equals("Usuario 3")));
        
	}
	
	@Test
	public void testUpdateUsuario(){
		
		Usuario usuario = usuarioRepository.findById(1L).orElse(null);
		
		usuario.setId(1L);
		usuario.setNome("João");
		
		usuarioRepository.save(usuario);
		
		assertEquals(1L, usuario.getId());
		assertTrue(usuario.getNome().equalsIgnoreCase("João"));
		
		usuario.setId(35L);
		usuario.setNome("Natasha");
		
		usuarioRepository.save(usuario);
		assertNotEquals(1L, usuario.getId());
		assertFalse(usuario.getNome().equalsIgnoreCase("João"));
		assertEquals(35L, usuario.getId());
		assertTrue(usuario.getNome().equalsIgnoreCase("Natasha"));
		
	}
	
	@Test
	public void deleteUsuario() {
		Categoria categoria = Factory.construtorCategoriaComArgumentos();
		
		assertEquals(1L, categoria.getId());
		assertTrue(categoria.getNome().equalsIgnoreCase("Energia"));
		
		usuarioRepository.deleteById(1L);
		
		Optional<Usuario> result = usuarioRepository.findById(existingId);
		
		assertFalse(result.isPresent());
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

		Usuario usuario = Factory.construtorUsuarioVazio();
		usuario.setId(null);
		
		usuario = usuarioRepository.save(usuario);
		
		assertNotNull(usuario);
		assertEquals(contTotalUsuarios + 1L, usuario.getId());
	}
	
	@Test
	public void OneToManyRelationshipCategoriaForDespesa () {
		Usuario usuario = Factory.construtorUsuarioComArgumentosComDespesa();
		
		usuarioRepository.save(usuario);
		
		Usuario usuarioRelacao = usuarioRepository.findById(usuario.getId()).orElse(null); 
		assertNotNull(usuarioRelacao);
		assertEquals(1, usuarioRelacao.getDespesas().size());
		
		//Verificando se a relação biderecional está funcionando.
		for(Despesa despesa : usuario.getDespesas()) {
			assertEquals(usuarioRelacao, despesa.getUsuario());
		}
	}
}

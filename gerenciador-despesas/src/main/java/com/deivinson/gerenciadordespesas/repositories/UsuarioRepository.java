package com.deivinson.gerenciadordespesas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deivinson.gerenciadordespesas.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}

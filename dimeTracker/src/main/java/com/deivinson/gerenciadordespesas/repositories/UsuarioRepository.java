package com.deivinson.gerenciadordespesas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deivinson.gerenciadordespesas.entities.User;

public interface UsuarioRepository extends JpaRepository<User, Long> {

}

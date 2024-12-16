package com.amanda.lireralura.repositories;

import java.util.Optional;

import com.amanda.lireralura.model.Livro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {

    Optional<Livro> findByIdAndCategoriaId(Integer idLivro, Integer idCategoria);
    
}

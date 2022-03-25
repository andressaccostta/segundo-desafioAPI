package com.example.demo.repository;
import com.example.demo.model.Aluno;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    
}

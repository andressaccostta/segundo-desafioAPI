package com.example.demo.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.model.Disciplina;
import com.example.demo.model.exception.ResourceNotFoundException;
import com.example.demo.repository.DisciplinaRepository_old;
import com.example.demo.shared.DisciplinaDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService { 
@Autowired
private DisciplinaRepository_old disciplinaRepository;

/** Metodo para retornar uma lista de disciplinas
 * 
 * @return Lista de disciplinas
 */

public List<DisciplinaDTO> obterTodos() {
    //Retorna uma lista de disciplinas Model
    List<Disciplina> disciplinas =  disciplinaRepository.obterTodos();

    return disciplinas.stream()
   .map(disciplina  -> new ModelMapper().map(disciplina, DisciplinaDTO.class))
   .collect(Collectors.toList());

}

/**
 * Metodo para retornar a disciplina encontrada pelo seu id
 * @param id a disciplina que sera localizada
 * @return retorna uma disciplina caso tenha sido encontrada
 */

// Obtendo Optional de disciplina pelo id
public Optional<DisciplinaDTO> obterPorId(Integer id){
    Optional<Disciplina> disciplina = disciplinaRepository.obterPorId(id);

// Se não encontrar, lança exception
    if(disciplina.isEmpty()){
        throw new ResourceNotFoundException("Disciplina com id: " + "não encontrado");
    }

// convertendo meu Option de disciplina em uma disciplinaDTO
    DisciplinaDTO dto = new ModelMapper().map(disciplina.get(), DisciplinaDTO.class);

// cirando e retornando um optional de disciplinadto
     return Optional.of(dto);
}



/**
 * Metodo para adicionar a disciplina na lista
 * @param disciplina que sera adicionada
 * @return retorna a disciplina que foi adicionada
 */

public DisciplinaDTO adicionar(DisciplinaDTO disciplinaDto){
   disciplinaDto.setId(null);
    //Criar um objeto de mapeamento
    ModelMapper mapper = new ModelMapper();

    // Converter a nossa DisciplinaDto em uma disciplina
    Disciplina disciplina = mapper.map(disciplinaDto, Disciplina.class);

    // Salvar a disciplina no banco
    disciplina = disciplinaRepository.save(disciplina);

    disciplinaDto.setId(disciplina.getId());

    // Retornar diciplinaDto atualizada
    return disciplinaDto;

}

/**
 * Metodo para deletar a disciplina por id
 * @param id da disciplina a ser deletada
 */

public void deletar(Integer id){
    Optional<Disciplina> disciplina = disciplinaRepository.findById(id);

    // Se não existir lança uma exception
        if(disciplina.isEmpty()){
            throw new ResourceNotFoundException("Não foi possivel deletar a disciplina com o id: "+ id + "- Produto não existe");
        }
    // Deleta o aluno pelo id
     disciplinaRepository.deleteById(id);
}

/**
 * Metodo para atualizar a disciplina na lista
 * @param disciplina que sera atualizada
 * @return a disciplina apos atualizar na lista
 */
public DisciplinaDTO atualizar(Integer id, DisciplinaDTO disciplinaDto){
    // Passar o id para o alunoDto
        disciplinaDto.setId(id);

    // Criar um objeto de mapeamento
        ModelMapper mapper = new ModelMapper(); 

    // Converter o DTO em um aluno
        Disciplina disciplina = mapper.map(disciplinaDto, Disciplina.class);

    // Atualizar o aluno no Banco de dados
        disciplinaRepository.save(disciplina);

    //Retornar o alunoDto atualizado
    return disciplinaDto;
}

}
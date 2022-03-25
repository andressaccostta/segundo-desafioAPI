package com.example.demo.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import com.example.demo.model.Aluno;
import com.example.demo.model.exception.ResourceNotFoundException;
import com.example.demo.repository.AlunoRepository_old;
import com.example.demo.shared.AlunoDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    
    
    @Service
    public class AlunoService{
    @Autowired
    private  AlunoRepository_old alunoRepository;

/** Metodo para retornar uma lista de alunos
 * 
 * @return Lista de Alunos
 */
    public List<AlunoDTO> obterTodos(){

        //Retorna uma lista de alunos Model
        List<Aluno> alunos =  alunoRepository.obterTodos();

         return alunos.stream()
        .map(aluno  -> new ModelMapper().map(aluno, AlunoDTO.class))
        .collect(Collectors.toList());
    
    }

/**
 * Metodo para retornar o aluno encontrado pelo seu id
 * @param id o aluno que sera localizado
 * @return retorna um aluno caso tenha sido encontrado
 */
    
// Obtendo Optional de aluno pelo id
    public Optional<AlunoDTO> obterPorId(Integer id){
        Optional<Aluno> aluno = alunoRepository.obterPorId(id);

// Se não encontrar, lança exception
        if(aluno.isEmpty()){
            throw new ResourceNotFoundException("Aluno com id: " + "não encontrado");
        }

// convertendo meu Option de aluno em um alunoDTO
        AlunoDTO dto = new ModelMapper().map(aluno.get(), AlunoDTO.class);

// cirando e retornando um optional de alunodto
         return Optional.of(dto);
}
    


/**
 * Metodo para adicionar o aluno na lista
 * @param aluno que sera adicionado
 * @return retorna aluno que foi adicionado
 */

    public AlunoDTO adicionar(AlunoDTO alunoDto){
        alunoDto.setId(null);
    //Criar um objeto de mapeamento
    ModelMapper mapper = new ModelMapper();

    // Converter o nosso AlunoDto em um aluno
    Aluno aluno = mapper.map(alunoDto, Aluno.class);

    // Salvar o aluno no banco
    aluno = alunoRepository.save(aluno);

    alunoDto.setId(aluno.getId());

    // Retornar o alunoDto atualizado
    return alunoDto;

}
    
/**
 * Metodo para deletar aluno por id
 * @param id do aluno a ser deletado
 */

    public void deletar(Integer id){
//verificar se o aluno existe
    Optional<Aluno> aluno = alunoRepository.findById(id);

// Se não existir lança uma exception
    if(aluno.isEmpty()){
        throw new ResourceNotFoundException("Não foi possivel deletar o aluno com o id: "+ id + "- Produto não existe");
    }
// Deleta o aluno pelo id
        alunoRepository.deletarById(id);
    }
    
/**
    * Metodo para atualizar o aluno na lista
    * @param aluno que sera atualizado
    * @return o aluno apos atualizar na lista
    */
    public AlunoDTO atualizar(Integer id, AlunoDTO alunoDto){
    // Passar o id para o alunoDto
        alunoDto.setId(id);

    // Criar um objeto de mapeamento
        ModelMapper mapper = new ModelMapper(); 

    // Converter o DTO em um aluno
        Aluno aluno = mapper.map(alunoDto, Aluno.class);

    // Atualizar o aluno no Banco de dados
        alunoRepository.save(aluno);

    //Retornar o alunoDto atualizado
        return alunoDto;
    }
    
}
package com.example.demo.repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Aluno;
import com.example.demo.model.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class AlunoRepository_old {

    private List<Aluno> alunos = new ArrayList<Aluno>();
    private Integer ultimoId = 0;


/***
 * Metodo para Retornar uma lista de alunos
 * @return Lista de alunos
 */

    public List<Aluno>obterTodos(){
            return alunos;
    }

/** Metodo que retorna o aluno encontrado pelo seu id
 * @param id da aluno que sera localizada
 * @return a aluno que tenha sido encontrada */

public Optional<Aluno>obterPorId(Integer id){
        return alunos
            .stream()
            .filter(aluno -> aluno.getId() == id)
            .findFirst();
}

/**Metodo para adicionar uma disciplina na lista
 * 
 * @param aluno que sera adicionada
 * @return retorna o aluno que foi adicionada na lista
 */


public Aluno adicionar( Aluno aluno){
    ultimoId++;
    aluno.setId(ultimoId);
    alunos.add(aluno);

    return aluno;
}

/*** Metodo para deletar a disciplina por ID
 * 
 * @param id id da disciplina a ser deletada
     
 */

public void deletar(Integer id){
    alunos.removeIf(aluno -> aluno.getId() == id);
}

/** Metodo para atualizar a discipina na lista
 * 
 * @param disciplina  que sera atualizada
 * @return retorna a disciplina apos atualizar a lista
 */


public Aluno atualizar(Aluno aluno){
    
    // Encontre aluno na lista
    Optional <Aluno> alunoEncontrado = obterPorId(aluno.getId());

    if(alunoEncontrado.isEmpty()){
        throw new ResourceNotFoundException("Produto nao encontrado");

    }

    // Eu tenho que remover o aluno antigo da lista
    deletar(aluno.getId());

    // Depois adicionar o aluno atualizado na lista
    adicionar(aluno);

    return aluno;
    }



public Aluno save(Aluno aluno) {
    return null;
}



public Optional<Aluno> findById(Integer id) {
    return null;
}



public void deletarById(Integer id) {
}


}

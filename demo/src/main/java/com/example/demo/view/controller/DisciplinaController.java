package com.example.demo.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.model.Disciplina;
import com.example.demo.service.DisciplinaService;
import com.example.demo.shared.DisciplinaDTO;
import com.example.demo.view.model.DisciplinaRequest;
import com.example.demo.view.model.DisciplinaResponse;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/disciplinas")

public class DisciplinaController {
    
@Autowired
private DisciplinaService disciplinaService;



@GetMapping
public List<DisciplinaResponse> obterTodos(){
    List<DisciplinaDTO> disciplinas = disciplinaService.obterTodos();

    ModelMapper mapper = new ModelMapper();

    List<DisciplinaResponse> resposta = disciplinas.stream()
    .map(disciplinaDto -> mapper.map(disciplinaDto, DisciplinaResponse.class))
    .collect(Collectors.toList());

    return resposta;
}


@GetMapping("/{id}")
public ResponseEntity<Optional<DisciplinaResponse>> obterPorId(@PathVariable Integer id){
       
    // try {
            Optional<DisciplinaDTO> dto = disciplinaService.obterPorId(id);
        
            DisciplinaResponse disciplina = new ModelMapper().map(dto.get() , DisciplinaResponse.class);

            return new ResponseEntity<>(Optional.of(disciplina), HttpStatus.OK);

    // } catch (Exception e) {
    //  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //}
}


@PostMapping
    public ResponseEntity<DisciplinaResponse> adicionar(@RequestBody DisciplinaRequest disciplinaReq){
        ModelMapper mapper = new ModelMapper();
        
        DisciplinaDTO disciplinaDto = mapper.map(disciplinaReq, DisciplinaDTO.class);

        disciplinaDto =  disciplinaService.adicionar(disciplinaDto);
        return new ResponseEntity<DisciplinaResponse>(mapper.map(disciplinaDto, DisciplinaResponse.class), HttpStatus.CREATED);
    }


@DeleteMapping("/{id}")
public ResponseEntity<?> deletar(@PathVariable Integer id){
    disciplinaService.deletar(id);
   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}


@PutMapping
public ResponseEntity<DisciplinaResponse>atualizar(@RequestBody DisciplinaRequest disciplinaReq, @PathVariable Integer id){
          
    ModelMapper mapper = new ModelMapper();
    DisciplinaDTO disciplinaDto = mapper.map(disciplinaReq,DisciplinaDTO.class);

    disciplinaDto = disciplinaService.atualizar(id, disciplinaDto);

   return new ResponseEntity<>(
       mapper.map(disciplinaDto, DisciplinaResponse.class),
       HttpStatus.OK);
    
}







}

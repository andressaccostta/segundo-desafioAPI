package com.example.demo.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.model.Aluno;
import com.example.demo.service.AlunoService;
import com.example.demo.shared.AlunoDTO;
import com.example.demo.view.model.AlunoRequest;
import com.example.demo.view.model.AlunoResponse;

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
    @RequestMapping("/api/alunos")
    public class AlunoController {
        
    @Autowired
    private AlunoService alunoService;
    
    @GetMapping
    public ResponseEntity<List<AlunoResponse>> obterTodos(){
        List<AlunoDTO> alunos = alunoService.obterTodos();

        ModelMapper mapper = new ModelMapper();

        List<AlunoResponse> resposta = alunos.stream()
        .map(alunoDto -> mapper.map(alunoDto, AlunoResponse.class))
        .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);

    }
    
    @GetMapping("/{id}")
     public ResponseEntity<Optional<AlunoResponse>>obterPorId(@PathVariable Integer id){
       
        // try {
                Optional<AlunoDTO> dto = alunoService.obterPorId(id);
            
                AlunoResponse aluno = new ModelMapper().map(dto.get(), AlunoResponse.class);

                return new ResponseEntity<>(Optional.of(aluno), HttpStatus.OK);

        // } catch (Exception e) {
        //  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        //}
     }
       
    
     @PostMapping
    public ResponseEntity<AlunoResponse> adicionar(@RequestBody AlunoRequest alunoReq){
        ModelMapper mapper = new ModelMapper();
        
        AlunoDTO alunoDto = mapper.map(alunoReq, AlunoDTO.class);

        alunoDto =  alunoService.adicionar(alunoDto);
        return new ResponseEntity<AlunoResponse>(mapper.map(alunoDto, AlunoResponse.class), HttpStatus.CREATED);

    }
    
    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id){
            alunoService.deletar(id);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping ("/{id}")
    public ResponseEntity<AlunoResponse> atualizar(@RequestBody AlunoRequest alunoReq, @PathVariable Integer id){
          
        ModelMapper mapper = new ModelMapper();
        AlunoDTO alunoDto = mapper.map(alunoReq,AlunoDTO.class);

       alunoDto = alunoService.atualizar(id, alunoDto);

       return new ResponseEntity<>(
           mapper.map(alunoDto, AlunoResponse.class),
           HttpStatus.OK);
        
    }


    }

package com.algaworks.comercial.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.algaworks.comercial.model.Oportunidade;
import com.algaworks.comercial.repository.OportunidadeRepository;

//get http://localhost:8080/oportunidades
@CrossOrigin //("http://localhost:4200")
@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {
	
	@Autowired
	private OportunidadeRepository oportunidades;

	@GetMapping
	public List<Oportunidade> listar() {
		
		return oportunidades.findAll();
		/*
		 * Oportunidade oportunidade = new Oportunidade();
		 * oportunidade.setId(2L);
		 * oportunidade.setDescricao("Desenvolvimento de Software");
		 * oportunidade.setNomeProspecto("FV System"); oportunidade.setValor(new
		 * BigDecimal(55000));
		 * 
		 * oportunidade.setId(1L);
		 * oportunidade.setDescricao("Desenvolvimento de ERP com Angular e Spring");
		 * oportunidade.setNomeProspecto("Grupo Logística Brasil");
		 * oportunidade.setValor(new BigDecimal(490000));
		 * 
		 * 
		 * //return "Fernando Vieira - Spring Tool suite"; //return "Hello"; return
		 * Arrays.asList(oportunidade);
		 */
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Oportunidade> buscar(@PathVariable Long id) {
	//public Optional<Oportunidade> buscar(@PathVariable Long id) {
		Optional<Oportunidade> oportunidade = oportunidades.findById(id); 
		
		if (oportunidade.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oportunidade.get());
		//return oportunidades.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Oportunidade adicionar(@Valid @RequestBody Oportunidade oportunidade) {
		Optional<Oportunidade> oportunidadeExistente = oportunidades.
				findByDescricaoAndNomeProspecto(oportunidade.getDescricao(), oportunidade.getNomeProspecto());
		if (oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma oportunidade igual"); 
		}
		
		return oportunidades.save(oportunidade);
	}
}

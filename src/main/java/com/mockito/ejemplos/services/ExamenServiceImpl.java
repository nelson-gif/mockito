package com.mockito.ejemplos.services;

import java.util.List;
import java.util.Optional;

import com.mockito.ejemplos.models.Examen;
import com.mockito.ejemplos.repositories.*;

public class ExamenServiceImpl implements ExamenService{

	private ExamenRepository examenRepository;
	private PreguntaRepository preguntaRepository;
	
	
	public ExamenServiceImpl(ExamenRepository examenRepository, PreguntaRepository preguntaRepository) {
		this.examenRepository = examenRepository;
		this.preguntaRepository = preguntaRepository;
	}



	@Override
	public Optional<Examen> findExampenPorNombre(String nombre) {
		return examenRepository.findAll()
				.stream()
				.filter(e -> e.getNombre().contains(nombre))
				.findFirst();
	}


	@Override
	public Examen findExamenPorNombreConPregunas(String nombre) {
		Optional<Examen> examenOptional = findExampenPorNombre(nombre);
		Examen examen = null;
		
		if(examenOptional.isPresent()) {
			examen = examenOptional.orElseThrow();
			List<String> preguntas = preguntaRepository.findPreguntasPorExamenId(examen.getId());
			examen.setPreguntas(preguntas);
		}
		
		return examen;
		
	}

}

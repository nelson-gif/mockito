package com.mockito.ejemplos.services;

import java.util.Optional;

import com.mockito.ejemplos.models.Examen;
import com.mockito.ejemplos.repositories.*;

public class ExamenServiceImpl implements ExamenService{

	private ExamenRepository examenRepository;
	
	
	
	public ExamenServiceImpl(ExamenRepository examenRepository) {
		this.examenRepository = examenRepository;
	}



	@Override
	public Optional<Examen> findExampenPorNombre(String nombre) {
		return examenRepository.findAll()
				.stream()
				.filter(e -> e.getNombre().contains(nombre))
				.findFirst();
	}

}

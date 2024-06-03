package com.mockito.ejemplos.services;

import java.util.Optional;

import com.mockito.ejemplos.models.Examen;

public interface ExamenService {
	
	Optional<Examen> findExampenPorNombre(String nombre);

}

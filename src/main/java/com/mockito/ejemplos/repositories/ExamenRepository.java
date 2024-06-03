package com.mockito.ejemplos.repositories;

import java.util.*;

import com.mockito.ejemplos.models.Examen;

public interface ExamenRepository {
	
	Examen guardar(Examen examen);
	
	List<Examen> findAll();
	
}

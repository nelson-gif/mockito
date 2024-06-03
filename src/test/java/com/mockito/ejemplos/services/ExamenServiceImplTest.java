package com.mockito.ejemplos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.*;

import com.mockito.ejemplos.models.Examen;
import com.mockito.ejemplos.repositories.*;

public class ExamenServiceImplTest {
	
	@Test
	void findExampenPorNombre() {
		ExamenRepository repository = mock(ExamenRepository.class);
		ExamenService service = new ExamenServiceImpl(repository);
		List<Examen> datos = Arrays.asList(new Examen(5L, "Matematicas"), new Examen(6L, "Lenguaje"), new Examen(7L, "Historia"));;
		
		when(repository.findAll()).thenReturn(datos);
		
		Examen examen = service.findExampenPorNombre("Matematicas");
		
		assertNotNull(examen);
		assertEquals(5L, examen.getId());
		assertEquals("Matematicas", examen.getNombre());
	}
	
}

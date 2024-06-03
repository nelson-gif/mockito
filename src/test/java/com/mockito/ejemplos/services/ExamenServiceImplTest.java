package com.mockito.ejemplos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mockito.ejemplos.models.Examen;
import com.mockito.ejemplos.repositories.*;

@ExtendWith(MockitoExtension.class)
public class ExamenServiceImplTest {
	
	@Mock
	ExamenRepository repository;
	@Mock
	PreguntaRepository preguntaRepository;
	
	@InjectMocks
	ExamenServiceImpl service;
	
	
	@BeforeEach
	void setUp() {
		//MockitoAnnotations.openMocks(this);
		
//		repository = mock(ExamenRepository.class);
//		preguntaRepository = mock(PreguntaRepository.class);
//		service = new ExamenServiceImpl(repository, preguntaRepository);
		
	}
	
	@Test
	void findExampenPorNombre() {
		
		
		when(repository.findAll()).thenReturn(Datos.EXAMENES);
		
		Optional<Examen> examen = service.findExampenPorNombre("Matematicas");
		
		assertTrue(examen.isPresent());
		assertEquals(5L, examen.orElseThrow().getId());
		assertEquals("Matematicas", examen.orElseThrow().getNombre());
	}

	@Test
	void findExampenPorNombreListaVacia() {
		List<Examen> datos = Collections.emptyList();
		
		when(repository.findAll()).thenReturn(datos);
		
		Optional<Examen> examen = service.findExampenPorNombre("Matematicas");
		
		assertFalse(examen.isPresent());
	}
	
	@Test
	void testPreguntaExamen() {
		when(repository.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
		Examen examen = service.findExamenPorNombreConPregunas("Historia");
		
		assertEquals(5, examen.getPreguntas().size());
		assertTrue(examen.getPreguntas().contains("aritmetica"));
		
	}
	
	@Test
	void testPreguntaExamenVerify() {
		when(repository.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
		Examen examen = service.findExamenPorNombreConPregunas("Historia");
		
		verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
		verify(repository).findAll();
		assertEquals(5, examen.getPreguntas().size());
		assertTrue(examen.getPreguntas().contains("aritmetica"));
		
	}
	
	@Test
	void testNoExiteExamenVerify() {
		when(repository.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
		Examen examen = service.findExamenPorNombreConPregunas("Historia2");
		
		verify(repository).findAll();
		assertNull(examen);
		verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
		
	}
	
}

package com.mockito.ejemplos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.internal.stubbing.answers.AnswersWithDelay;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

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
	
//	@Test
//	void TestguardarExamenFailGuardarVarias() {
//		when(repository.guardar(any(Examen.class))).thenReturn(Datos.EXAMEN);
//		Examen examen = service.guardar(Datos.EXAMEN);
//		assertNotNull(examen.getId());
//		assertEquals("Fisica", examen.getNombre());
//		assertEquals(8L, examen.getId());
//		verify(repository).guardar(any(Examen.class));
//		verify(preguntaRepository).guardarVarias(anyList());;
//		
//	}
//	
	@Test
	void TestguardarExamen() {
		//Given
		Examen newExamen = Datos.EXAMEN;
		newExamen.setPreguntas(Datos.PREGUNTAS);
		when(repository.guardar(any(Examen.class))).then( new Answer<Examen>() {

			Long secuencia = 8L;
			
			@Override
			public Examen answer(InvocationOnMock invocation) throws Throwable {
				Examen examen = invocation.getArgument(0);
				examen.setId(secuencia++);
				return examen;
			}
			
		});
		
		//When
		Examen examen = service.guardar(newExamen);
		
		//then
		assertNotNull(examen.getId());
		assertEquals("Fisica", examen.getNombre());
		assertEquals(8L, examen.getId());
		verify(repository).guardar(any(Examen.class));
		verify(preguntaRepository).guardarVarias(anyList());;
		
	}
	
}

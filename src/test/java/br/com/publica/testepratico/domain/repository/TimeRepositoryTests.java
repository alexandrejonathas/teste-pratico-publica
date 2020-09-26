package br.com.publica.testepratico.domain.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.TransactionSystemException;

import br.com.publica.testepratico.domain.exception.EntidadeEmUsoException;
import br.com.publica.testepratico.domain.exception.EntidadeExistenteException;
import br.com.publica.testepratico.domain.exception.EntidadeNaoEncontradaException;
import br.com.publica.testepratico.domain.model.Jogo;
import br.com.publica.testepratico.domain.model.Time;
import br.com.publica.testepratico.domain.service.JogoService;
import br.com.publica.testepratico.domain.service.TimeService;
import br.com.publica.testepratico.utils.DatabaseCleaner;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class TimeRepositoryTests {

	@Autowired
	private TimeService timeService;
	
	@Autowired
	private JogoService jogoService;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@BeforeEach
	public void init() {
		databaseCleaner.clearTables();
	}
	
	@Test
	public void deve_criar_um_time() {
		Time time = new Time();
		time.setNome("Time 1");
		
		time = timeService.salvar(time);
		
		Assertions.assertNotNull(time);
		Assertions.assertNotNull(time.getId());
	}
	
	@Test
	public void deve_falhar_ao_criar_um_time_sem_nome() {
		Assertions.assertThrows(TransactionSystemException.class, () -> { timeService.salvar(new Time()); });
	}
	
	@Test
	public void deve_buscar_um_time_por_id() {
		Time time = new Time();
		time.setNome("Time 1");
		
		time = timeService.salvar(time);
		
		Time timeEncontrado = timeService.buscar(time.getId());
		Assertions.assertNotNull(timeEncontrado);
		Assertions.assertNotNull(timeEncontrado.getId());		
	}
	
	@Test
	public void deve_retornar_null_ao_buscar_um_time_por_id_inexistente() {	
		Time timeEncontrado = timeService.buscar(100L);
		Assertions.assertNull(timeEncontrado);		
	}
	
	@Test
	public void deve_falhar_ao_excluir_time_em_uso() {
		Time time1 = new Time();
		time1.setNome("Time 1");
		time1 = timeService.salvar(time1);
		
		final Long time1Id = time1.getId();
		
		Time time2 = new Time();
		time2.setNome("Time 2");
		time2 = timeService.salvar(time2);		
		
		final Long time2Id = time2.getId();
		
		Jogo jogo = new Jogo();
		jogo.setData(LocalDate.now());
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);
		
		jogoService.salvar(jogo);
		
		Assertions.assertThrows(EntidadeEmUsoException.class, () -> { timeService.deletar(time1Id); });
		Assertions.assertThrows(EntidadeEmUsoException.class, () -> { timeService.deletar(time2Id); });
	}	
	
	@Test
	public void deve_falhar_ao_excluir_time_inexistente() {
		Assertions.assertThrows(EntidadeNaoEncontradaException.class, () -> { timeService.deletar(100L); });
	}
	
	@Test
	public void deve_falhar_ao_criar_um_time_existente() {
		Time time = new Time();
		time.setNome("Time 1");
		
		timeService.salvar(time);
		
		Time timeExistente = new Time();
		timeExistente.setNome("Time 1");
		
		final Time timeSalvar = timeExistente;
		
		Assertions.assertThrows(EntidadeExistenteException.class, () -> { timeService.salvar(timeSalvar); });
	}	
	
}

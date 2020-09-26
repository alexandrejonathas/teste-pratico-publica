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
import br.com.publica.testepratico.domain.model.Pontuacao;
import br.com.publica.testepratico.domain.model.Time;
import br.com.publica.testepratico.domain.service.JogoService;
import br.com.publica.testepratico.domain.service.PontuacaoService;
import br.com.publica.testepratico.domain.service.TimeService;
import br.com.publica.testepratico.utils.DatabaseCleaner;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class JogoRepositoryTests {

	@Autowired
	private TimeService timeService;
	
	@Autowired
	private JogoService jogoService;
	
	@Autowired
	private PontuacaoService pontuacaoService;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	private Time time1;
	private Time time2;
	
	@BeforeEach
	public void init() {
		databaseCleaner.clearTables();
		time1 = new Time();
		time1.setNome("Time 1");
		time2 = new Time();
		time2.setNome("Time 2");
	}
	
	@Test
	public void deve_criar_um_jogo() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		
		Jogo jogo = new Jogo();
		jogo.setData(LocalDate.now());
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);
		
		jogo = jogoService.salvar(jogo);
		
		Assertions.assertNotNull(jogo);
		Assertions.assertNotNull(jogo.getId());
	}
	
	@Test
	public void deve_falhar_ao_criar_um_jogo_sem_data() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		
		Jogo jogo = new Jogo();
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);
		
		final Jogo jogoSalvar = jogo;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { jogoService.salvar(jogoSalvar); });
	}
	
	@Test
	public void deve_falhar_ao_criar_um_jogo_sem_time_da_casa() {
		time2 = timeService.salvar(time2);
		
		Jogo jogo = new Jogo();
		jogo.setData(LocalDate.now());
		jogo.setTimeVisitante(time2);
		
		final Jogo jogoSalvar = jogo;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { jogoService.salvar(jogoSalvar); });
	}
	
	@Test
	public void deve_falhar_ao_criar_um_jogo_sem_time_visitante() {
		time1 = timeService.salvar(time1);
		
		Jogo jogo = new Jogo();
		jogo.setData(LocalDate.now());
		jogo.setTimeCasa(time1);
		
		final Jogo jogoSalvar = jogo;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { jogoService.salvar(jogoSalvar); });
	}	
	
	
	@Test
	public void deve_buscar_um_jogo_por_id() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		
		Jogo jogo = new Jogo();
		jogo.setData(LocalDate.now());
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);
		
		jogo = jogoService.salvar(jogo);
		
		Jogo jogoEncontrado = jogoService.buscar(jogo.getId());
		Assertions.assertNotNull(jogoEncontrado);
		Assertions.assertNotNull(jogoEncontrado.getId());		
	}
	
	@Test
	public void deve_retornar_null_ao_buscar_um_jogo_por_id_inexistente() {	
		Jogo jogoEncontrado = jogoService.buscar(100L);
		Assertions.assertNull(jogoEncontrado);		
	}
	
	@Test
	public void deve_falhar_ao_excluir_jogo_em_uso() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		
		Jogo jogo = new Jogo();
		jogo.setData(LocalDate.now());
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);
		
		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(10);
		pontuacao.setMaximoTemporada(14);
		pontuacao.setQuebraRecordeMinimo(0);
		pontuacao.setQuebraRecordeMaximo(1);
		
		pontuacao = pontuacaoService.salvar(pontuacao);
		
		final Long jogoId = jogo.getId();
		Assertions.assertThrows(EntidadeEmUsoException.class, () -> { jogoService.deletar(jogoId); });
	}	
	
	@Test
	public void deve_falhar_ao_excluir_jogo_inexistente() {
		Assertions.assertThrows(EntidadeNaoEncontradaException.class, () -> { jogoService.deletar(100L); });
	}
	
	@Test
	public void deve_falhar_ao_tentar_criar_um_jogo_existente() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		
		Jogo jogo = new Jogo();
		jogo.setData(LocalDate.now());
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);
		
		jogo = jogoService.salvar(jogo);
		jogo.setId(null);
		
		final Jogo jogoExistente = jogo;
		Assertions.assertThrows(EntidadeExistenteException.class, () -> { jogoService.salvar(jogoExistente); });
	}	
	
}

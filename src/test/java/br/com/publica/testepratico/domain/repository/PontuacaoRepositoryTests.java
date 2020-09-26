package br.com.publica.testepratico.domain.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.TransactionSystemException;

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
public class PontuacaoRepositoryTests {

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
	private Jogo jogo;
	
	@BeforeEach
	public void init() {
		databaseCleaner.clearTables();
		
		time1 = new Time();
		time1.setNome("Time 1");
		
		time2 = new Time();
		time2.setNome("Time 2");
		
		jogo = new Jogo();
		jogo.setData(LocalDate.now());
	}
	
	@Test
	public void deve_criar_uma_pontuacao() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		pontuacao = pontuacaoService.salvar(pontuacao);
		
		Assertions.assertNotNull(pontuacao);
		Assertions.assertNotNull(pontuacao.getId());
	}
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_sem_jogo() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_sem_placar() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_com_placar_menor_que_zero() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(-1);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}	
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_com_placar_maio_que_mil() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(1001);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}		
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_sem_minimo_da_temporada() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_com_minimo_da_temporada_menor_que_zero() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(-1);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}	
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_com_minimo_da_temporada_maior_que_mil() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(1001);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}	
	
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_sem_maximo_da_temporada() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_com_maximo_da_temporada_menor_que_zero() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(-1);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}	
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_com_maximo_da_temporada_maio_que_mil() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(1001);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}	
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_sem_quebra_recorde_minimo() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_com_quebra_recorde_minimo_menor_que_zero() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(-1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}	
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_com_quebra_recorde_minimo_maior_que_mil() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1001);
		pontuacao.setQuebraRecordeMaximo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}	
	
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_sem_quebra_recorde_maximo() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}	
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_com_quebra_recorde_maximo_menor_que_zero() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(-1);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}	
	
	@Test
	public void deve_falhar_ao_criar_uma_pontuacao_com_quebra_recorde_maximo_maior_que_mil() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1001);
		
		final Pontuacao pontuacaoSalvar = pontuacao;		
		
		Assertions.assertThrows(TransactionSystemException.class, () -> { pontuacaoService.salvar(pontuacaoSalvar); });
	}	
	
	@Test
	public void deve_buscar_uma_pontuacao_por_id() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		pontuacao = pontuacaoService.salvar(pontuacao);
		
		Pontuacao pontuacaoEncontrada = pontuacaoService.buscar(pontuacao.getId());
		Assertions.assertNotNull(pontuacaoEncontrada);
		Assertions.assertNotNull(pontuacaoEncontrada.getId());		
	}
	
	@Test
	public void deve_retornar_null_ao_buscar_uma_pontuacao_por_id_inexistente() {	
		Pontuacao pontuacaoEncontrada = pontuacaoService.buscar(100L);
		Assertions.assertNull(pontuacaoEncontrada);		
	}	
	
	@Test
	public void deve_falhar_ao_excluir_uma_pontuacao_inexistente() {
		Assertions.assertThrows(EntidadeNaoEncontradaException.class, () -> { pontuacaoService.deletar(100L); });
	}
	
	@Test
	public void deve_falhar_ao_tentar_criar_uma_pontuacao_existente() {
		time1 = timeService.salvar(time1);
		time2 = timeService.salvar(time2);
		jogo.setTimeCasa(time1);
		jogo.setTimeVisitante(time2);		

		jogo = jogoService.salvar(jogo);
		
		Pontuacao pontuacao = new Pontuacao();
		pontuacao.setJogo(jogo);
		pontuacao.setPlacar(10);
		pontuacao.setMinimoTemporada(5);
		pontuacao.setMaximoTemporada(15);
		pontuacao.setQuebraRecordeMinimo(1);
		pontuacao.setQuebraRecordeMaximo(1);
		
		pontuacao = pontuacaoService.salvar(pontuacao);
		pontuacao.setId(null);
		
		final Pontuacao pontuacaoExistente = pontuacao;
		Assertions.assertThrows(EntidadeExistenteException.class, () -> { pontuacaoService.salvar(pontuacaoExistente); });
	}	
	
}

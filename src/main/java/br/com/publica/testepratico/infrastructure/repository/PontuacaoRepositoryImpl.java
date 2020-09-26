package br.com.publica.testepratico.infrastructure.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.publica.testepratico.domain.filter.PontuacaoFilter;
import br.com.publica.testepratico.domain.model.Jogo;
import br.com.publica.testepratico.domain.model.Pontuacao;
import br.com.publica.testepratico.domain.model.Time;
import br.com.publica.testepratico.domain.model.dto.PontoMes;
import br.com.publica.testepratico.domain.model.dto.RecordeQuebrado;
import br.com.publica.testepratico.domain.repository.PontuacaoRepositoryQueries;

@Repository
public class PontuacaoRepositoryImpl implements PontuacaoRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;	
	
	@Override
	public List<Pontuacao> filtrar(PontuacaoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pontuacao> criteria = builder.createQuery(Pontuacao.class);
		
		Root<Pontuacao> root = criteria.from(Pontuacao.class);		
		
		Join<Pontuacao, Jogo> jogo = root.join("jogo", JoinType.INNER);		
		
		Join<Jogo, Time> timeCasa = jogo.join("timeCasa", JoinType.INNER);
		
		Join<Jogo, Time> timeVisitante = jogo.join("timeVisitante", JoinType.INNER);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(filter.getDataInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(jogo.get("data"), filter.getDataInicio()));
		}
		
		if(filter.getDataFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(jogo.get("data"), filter.getDataFim()));
		}		
		
		if(!StringUtils.isEmpty(filter.getTimeCasa())) {
			String filtro = "%"+filter.getTimeCasa()+"%";
			predicates.add(builder.like(timeCasa.get("nome"), filtro)); 
		}
		
		if(!StringUtils.isEmpty(filter.getTimeVisitante())) {
			String filtro = "%"+filter.getTimeVisitante()+"%";
			predicates.add(builder.like(timeVisitante.get("nome"), filtro));
		}
		
		criteria.where(predicates.toArray(new Predicate[predicates.size()]));
		criteria.orderBy(builder.asc(jogo.get("data")));
		
		TypedQuery<Pontuacao> query = manager.createQuery(criteria);			
		
		
		return query.getResultList();		
		
	}

	@Override
	public RecordeQuebrado getQuantidadeRecordeQuebrados() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<RecordeQuebrado> criteria = builder.createQuery(RecordeQuebrado.class);
		
		Root<Pontuacao> root = criteria.from(Pontuacao.class);
		
		
		CompoundSelection<RecordeQuebrado> selection = builder.construct(RecordeQuebrado.class, 
				builder.sum(root.get("quebraRecordeMinimo")), 
				builder.sum(root.get("quebraRecordeMaximo")));
		
		criteria.select(selection);		
		
		return manager.createQuery(criteria).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PontoMes> getTotalPontosPorMes() {
		List<PontoMes> pontosMes = manager.createNamedQuery("Pontuacao.PontosPorMes").getResultList();
		
		
		LocalDate hoje = LocalDate.now();
		for(int i = 1; i <= 6; i++) {
			
			String mes = String.format("%d/%02d", hoje.getYear(), hoje.getMonthValue());
			
			boolean possuiMes = pontosMes.stream().filter(p -> p.getMes().equals(mes)).findAny().isPresent();
			
			if(!possuiMes) {
				pontosMes.add(i-1, new PontoMes(mes, 0));
			}
			
			hoje = hoje.minusMonths(1);
		}
		
		
		return pontosMes;
	}

}

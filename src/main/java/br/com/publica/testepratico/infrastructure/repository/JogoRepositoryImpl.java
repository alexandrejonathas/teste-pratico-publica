package br.com.publica.testepratico.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.publica.testepratico.domain.filter.JogoFilter;
import br.com.publica.testepratico.domain.model.Jogo;
import br.com.publica.testepratico.domain.model.Time;
import br.com.publica.testepratico.domain.repository.JogoRepositoryQueries;

@Repository
public class JogoRepositoryImpl implements JogoRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;	
	
	@Override
	public List<Jogo> filtrar(JogoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Jogo> criteria = builder.createQuery(Jogo.class);
		
		Root<Jogo> root = criteria.from(Jogo.class);
		
		root.fetch("timeCasa", JoinType.INNER);
		root.fetch("timeVisitante", JoinType.INNER);
		
		Join<Jogo, Time> timeCasa = root.join("timeCasa");
		Join<Jogo, Time> timeVisitante = root.join("timeVisitante");
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(filter.getNome())) {
			String filtro = "%"+filter.getNome()+"%";
			predicates.add(builder.or(builder.like(timeCasa.get("nome"), filtro) ,builder.like(timeVisitante.get("nome"), filtro)));
		}
		
		criteria.distinct(true);
		
		criteria.where(predicates.toArray(new Predicate[predicates.size()]));
		criteria.orderBy(builder.asc(root.get("data")));
		
		TypedQuery<Jogo> query = manager.createQuery(criteria);			
		
		return query.getResultList();		
		
	}

}

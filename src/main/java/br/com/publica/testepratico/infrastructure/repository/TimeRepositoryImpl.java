package br.com.publica.testepratico.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.publica.testepratico.domain.filter.TimeFilter;
import br.com.publica.testepratico.domain.model.Time;
import br.com.publica.testepratico.domain.repository.TimeRepositoryQueries;

@Repository
public class TimeRepositoryImpl implements TimeRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;	
	
	@Override
	public List<Time> filtrar(TimeFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Time> criteria = builder.createQuery(Time.class);
		Root<Time> root = criteria.from(Time.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(filter.getNome())) {
			predicates.add(builder.like(root.get("nome"), "%"+filter.getNome()+"%"));
		}
		
		criteria.where(predicates.toArray(new Predicate[predicates.size()]));
		criteria.orderBy(builder.desc(root.get("nome")));
		
		TypedQuery<Time> query = manager.createQuery(criteria);			
		
		return query.getResultList();		
		
	}

}

package com.joonki.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.joonki.domain.Post;

@Repository
@Transactional
public class PostRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(Post post) {
		int depth = 0;
		int sort = 0;
		if(post.getParentNum() > 0) {
			// Set depth
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Post> criteria = builder.createQuery(Post.class);
			Root<Post> root = criteria.from(Post.class);
			criteria.where(builder.equal(root.get("id"), post.getParentNum()));
			Post parentPost = entityManager.createQuery(criteria).getSingleResult();
			depth = parentPost.getDepth() + 1;
			
			// Set order
			sort = parentPost.getSort() + 1;
			CriteriaUpdate<Post> sortCriteria = builder.createCriteriaUpdate(Post.class);
			Root<Post> sortRoot = sortCriteria.from(Post.class);
			Path<Integer> sortPath = sortRoot.get("sort");
			sortCriteria.set(sortPath, builder.sum(sortPath, 1));
			List<Predicate> predicateList = new ArrayList<>();
			predicateList.add(builder.equal(sortRoot.get("group"), parentPost.getGroup()));
			predicateList.add(builder.ge(sortPath, sort));
			sortCriteria.where(predicateList.toArray(new Predicate[]{}));
			entityManager.createQuery(sortCriteria).executeUpdate();
			//entityManager.createQuery("UPDATE post p SET p.sort = p.sort + 1 WHERE p.group = " + parentPost.getGroup() + " AND p.sort >= " + sort).executeUpdate();
		}
		
		post.setDepth(depth);
		post.setSort(sort);
		
		entityManager.persist(post);
		
		if(post.getGroup() == 0) {
			post.setGroup(post.getId());
		}
	}
	
	public void update(Post post) {
		entityManager.merge(post);
	}
	
	public List<Post> findAll(int limit, int offset) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> criteria = builder.createQuery(Post.class);
		Root<Post> root = criteria.from(Post.class);
		criteria.select(root);
		
		List<Order> orderList = new ArrayList<>();
		orderList.add(builder.desc(root.get("group")));
		orderList.add(builder.asc(root.get("sort")));
		criteria.orderBy(orderList);
		return entityManager.createQuery(criteria).setFirstResult(offset).setMaxResults(limit).getResultList();
	}
	
	public long getTotalCount() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		criteria.select(builder.count(criteria.from(Post.class)));
		return entityManager.createQuery(criteria).getSingleResult();
	}
}

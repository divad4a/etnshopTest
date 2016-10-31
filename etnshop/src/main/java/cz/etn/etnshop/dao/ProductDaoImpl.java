package cz.etn.etnshop.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

@Repository("productDao")
public class ProductDaoImpl extends AbstractDao implements ProductDao {

	@PostConstruct
	public void init() {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(this.getEntityManager());
		try {
			fullTextEntityManager.createIndexer(Product.class).startAndWait();
		} catch (InterruptedException ex) {

		}
	}

	public List<Product> getProducts() {
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		cq.from(Product.class);
		return this.getEntityManager().createQuery(cq).getResultList();
	}

	@Override
	public boolean editProduct(Product product) {
		this.getEntityManager().merge(product);

		return true;
	}

	@Override
	public boolean deleteProduct(Product product) {
		EntityManager em = this.getEntityManager();
		em.remove(em.contains(product) ? product : em.merge(product));

		return true;
	}

	@Override
	public int addProduct(Product product) {
		this.getEntityManager().persist(product);
		this.entityManager.flush();
		return product.getId();
	}

	@Override
	public List<Product> searchProducts(String keyword) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(this.getEntityManager());

		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Product.class).get();
		org.apache.lucene.search.Query luceneQuery = qb.keyword().fuzzy().onFields(Product.COLUMN_NAME, Product.COLUMN_SERIALNO).matching(keyword)
				.createQuery();

		javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Product.class);

		return (List<Product>) jpaQuery.getResultList();
	}
}

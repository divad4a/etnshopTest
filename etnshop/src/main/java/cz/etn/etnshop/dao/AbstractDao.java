package cz.etn.etnshop.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AbstractDao {

	// @Autowired
	// private SessionFactory sessionFactory;

	@PersistenceContext
	EntityManager entityManager;

	// protected Session getSession() {
	// entityManager.cre
	// return sessionFactory.getCurrentSession();
	// }
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}

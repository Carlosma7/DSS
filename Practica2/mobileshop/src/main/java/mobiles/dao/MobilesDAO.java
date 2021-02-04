package mobiles.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mobiles.models.JPAUtil;
import mobiles.models.Mobile;
import mobiles.models.ShoppingCart;

public class MobilesDAO {

	EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();

	// Save mobile
	public void save(Mobile m) {
		entity.getTransaction().begin();
		// Persist entity
		entity.persist(m);
		entity.getTransaction().commit();
	}

	// Edit mobile
	public void edit(Mobile m) {
		entity.getTransaction().begin();
		// Merge entity
		entity.merge(m);
		entity.getTransaction().commit();
	}

	// Search mobile
	public Mobile search(Long id) {
		Mobile m = new Mobile();
		// Find entity
		m = entity.find(Mobile.class, id);
		return m;
	}

	// Remove mobile
	public void remove(Long id) {
		Mobile m = new Mobile();
		// Find entity
		m = entity.find(Mobile.class, id);
		entity.getTransaction().begin();
		// Remove entity
		entity.remove(m);
		entity.getTransaction().commit();
	}


	// Get list of mobiles
	public List<Mobile> getListMobiles() {
		List<Mobile> mobileList = new ArrayList<>();
		// Query entity
		Query query = entity.createQuery("SELECT m FROM Mobile m");
		mobileList = query.getResultList();
		return mobileList;
	}
	
	// Get mobile by ID
	public Mobile getMobile(Long id) {
		Mobile m = new Mobile();
		// Query entity
		Query query = entity.createQuery("SELECT m FROM Mobile AS m WHERE m.id LIKE :id").setParameter("id", id);
		m = (Mobile) query.getResultList().get(0);
		return m;
	}

}

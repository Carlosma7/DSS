package mobiles.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mobiles.models.Admin;
import mobiles.models.JPAUtil;

public class AdminsDAO {

	EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();

	// Save admin
	public void save(Admin a) {
		entity.getTransaction().begin();
		// Persist entity
		entity.persist(a);
		entity.getTransaction().commit();
	}

	// Edit client
	public void edit(Admin a) {
		entity.getTransaction().begin();
		// Merge entity
		entity.merge(a);
		entity.getTransaction().commit();
	}

	// Search admin by ID
	public Admin search(Long id) {
		Admin a = new Admin();
		// Find entity
		a = entity.find(Admin.class, id);
		return a;
	}
	
	// Search admin by email and password
	public Admin search(String email, String password) {
		Admin a = new Admin();
		// Query entity
		Query q = entity.createQuery("SELECT a FROM Admin AS a WHERE a.email LIKE :email AND a.password LIKE :password", Admin.class).setParameter("email", email).setParameter("password", password);
		q.setMaxResults(1);
		if (q.getResultList().isEmpty()){
			return a;
		}
		a = (Admin) q.getResultList().get(0);
		return a;
	}
	
	// Search admin by email
	public Admin search(String email) {
		Admin a = new Admin();
		// Query entity
		Query q = entity.createQuery("SELECT a FROM Admin AS a WHERE a.email LIKE :email", Admin.class).setParameter("email", email);
		q.setMaxResults(1);
		if (q.getResultList().isEmpty()){
			return a;
		}
		a = (Admin) q.getResultList().get(0);
		return a;
	}

	/// Remove admin
	public void remove(Long id) {
		Admin c = new Admin();
		// Find entity
		c = entity.find(Admin.class, id);
		entity.getTransaction().begin();
		// Remove entity
		entity.remove(c);
		entity.getTransaction().commit();
	}

	// Get list of admins
	public List<Admin> getListAdmins() {
		List<Admin> adminList = new ArrayList<>();
		// Query entity
		Query q = entity.createQuery("SELECT a FROM Admin a");
		adminList = q.getResultList();
		return adminList;
	}

}

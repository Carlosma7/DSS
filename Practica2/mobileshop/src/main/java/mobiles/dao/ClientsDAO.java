package mobiles.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mobiles.models.Client;
import mobiles.models.JPAUtil;

public class ClientsDAO {

	EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();

	// Save client
	public void save(Client c) {
		entity.getTransaction().begin();
		// Persist entity
		entity.persist(c);
		entity.getTransaction().commit();
	}

	// Edit client
	public void edit(Client c) {
		entity.getTransaction().begin();
		// Merge entity
		entity.merge(c);
		entity.getTransaction().commit();
	}

	// Search client
	public Client search(Long id) {
		Client c = new Client();
		// Find entity
		c = entity.find(Client.class, id);
		return c;
	}
	
	// Search client by email and password
	public Client search(String email, String password) {
		Client c = new Client();
		// Query entity by email and password
		Query q = entity.createQuery("SELECT c FROM Client AS c WHERE c.email LIKE :email AND c.password LIKE :password", Client.class).setParameter("email", email).setParameter("password", password);
		q.setMaxResults(1);
		if (q.getResultList().isEmpty()){
			return c;
		}
		c = (Client) q.getResultList().get(0);
		return c;
	}
	
	// Search client
	public Client search(String email) {
		Client c = new Client();
		// Query entity
		Query q = entity.createQuery("SELECT c FROM Client AS c WHERE c.email LIKE :email", Client.class).setParameter("email", email);
		q.setMaxResults(1);
		if (q.getResultList().isEmpty()){
			return c;
		}
		c = (Client) q.getResultList().get(0);
		return c;
	}

	/// Remove client
	public void remove(Long id) {
		Client c = new Client();
		// Find entity
		c = entity.find(Client.class, id);
		entity.getTransaction().begin();
		// Remove entity
		entity.remove(c);
		entity.getTransaction().commit();
	}

	// Get list of clients
	public List<Client> getListlients() {
		List<Client> clientList = new ArrayList<>();
		// Query entity
		Query q = entity.createQuery("SELECT c FROM Client c");
		clientList = q.getResultList();
		return clientList;
	}

}

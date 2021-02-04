package mobiles.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mobiles.models.JPAUtil;
import mobiles.models.ShoppingCart;

public class ShoppingCartsDAO {

	EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();

	// Save shopping cart item
	public void save(ShoppingCart sc) {
		entity.getTransaction().begin();
		// Persist entity
		entity.persist(sc);
		entity.getTransaction().commit();
	}

	// Edit shopping cart item
	public void edit(ShoppingCart sc) {
		entity.getTransaction().begin();
		// Merge entity
		entity.merge(sc);
		entity.getTransaction().commit();
	}

	// Search shopping cart item
	public ShoppingCart search(Long id) {
		ShoppingCart sc = new ShoppingCart();
		// Find entity
		sc = entity.find(ShoppingCart.class, id);
		return sc;
	}

	// Remove shopping cart item
	public void remove(Long id) {
		ShoppingCart sc = new ShoppingCart();
		// Find entity
		sc = entity.find(ShoppingCart.class, id);
		entity.getTransaction().begin();
		// Remove entity
		entity.remove(sc);
		entity.getTransaction().commit();
	}

	// Get list of shopping cart items by client
	public List<ShoppingCart> getListShoppingCarts(String client) {
		List<ShoppingCart> shoppingCartList = new ArrayList<>();
		// Query entity by client
		Query query = entity.createQuery("SELECT sc FROM ShoppingCart sc WHERE sc.client LIKE :client").setParameter("client", client);
		shoppingCartList = query.getResultList();
		return shoppingCartList;
	}

}

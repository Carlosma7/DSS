package mobiles.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sun.jersey.api.client.ClientResponse.Status;

import mobiles.dao.MobilesDAO;
import mobiles.dao.ShoppingCartsDAO;
import mobiles.models.Mobile;
import mobiles.models.ShoppingCart;

@Path(value = "/shoppingcart")
public class RestServiceShoppingCarts {
	
	String msg = null;
	
	// Get list of items in shopping cart
	@POST
	@Path("/list")
	@Consumes("application/json")
	public Response getData(String client) {
		// Get list of items in shopping cart
		List<ShoppingCart> msg = new ShoppingCartsDAO().getListShoppingCarts(client);
		MobilesDAO mdao = new MobilesDAO();
		List<Mobile> cart = new ArrayList<>();
		Mobile m;
		// Get mobile refered by item
		for(ShoppingCart sc: msg) {
			m = mdao.getMobile(sc.getMobile());
			m.setId(sc.getId());
			cart.add(m);
		}
		
		// Return shopping cart
		return Response.status(201).entity(cart)
				.header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
				.build();
	}
	
	// Delete an item from the shopping cart
	@DELETE
    @Path("/{id}")
    @Consumes("application/json")
    public Response deleteItem(@PathParam("id") String itemId) {
		ShoppingCartsDAO sdao = new ShoppingCartsDAO();
		// Remove an item
		sdao.remove(Long.parseLong(itemId));
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
	
	// Add item to the shopping cart
	@POST
    @Path("/add")
    @Consumes("application/json")
    public Response addItem(ShoppingCart sc) {
		ShoppingCartsDAO sdao = new ShoppingCartsDAO();
		// Save item
		sdao.save(sc);
        return Response.ok().entity(sc)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
	
	
	

}

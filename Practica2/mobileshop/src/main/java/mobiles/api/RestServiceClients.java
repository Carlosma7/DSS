package mobiles.api;

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

import mobiles.dao.ClientsDAO;
import mobiles.models.Client;

@Path(value = "/clients")
public class RestServiceClients {
	
	String msg = null;

	// Login of a client	
	@POST
	@Path("/login")
    @Consumes("application/json")
    public Response loginClient(Client client) {
		ClientsDAO sdao = new ClientsDAO();
		// Search client by email and password
		Client c = sdao.search(client.getEmail(), client.getPassword());
		// Check if the client exists
		if (c.getEmail() == null){
			// Client doesnt exist
			return Response.status(404).build();
		}
		// Client exists
        return Response.ok().status(201)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
	
	// Register of a client
	@POST
	@Path("/register")
	@Consumes("application/json")
	public Response registerClient(Client client) {
		ClientsDAO sdao = new ClientsDAO();
		// Search client by email
		Client c = sdao.search(client.getEmail());
		// Check if the client email exists
		if (c.getEmail() != null){
			// Client email exists
			return Response.status(404).build();
		}
		// Save client
		sdao.save(client);
        
        return Response.ok().status(201).entity(client)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
	
	
	

}

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

import mobiles.dao.AdminsDAO;
import mobiles.models.Admin;

@Path(value = "/admins")
public class RestServiceAdmins {
	
	String msg = null;
	
	// Login of an admin
	@POST
	@Path("/login")
    @Consumes("application/json")
    public Response loginAdmin(Admin admin) {
		AdminsDAO sdao = new AdminsDAO();
		// Search admin by email and password
		Admin a = sdao.search(admin.getEmail(), admin.getPassword());
		// Check if the admin exists
		if (a.getEmail() == null){
			// Admin doesnt exist
			return Response.status(404).build();
		}
		// Admin exists
        return Response.ok().status(201)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
	
	// Register of an admin
	@POST
	@Path("/register")
	@Consumes("application/json")
	public Response registerAdmin(Admin admin) {
		AdminsDAO sdao = new AdminsDAO();
		// Search admin by email
		Admin a = sdao.search(admin.getEmail());
		// Check if the admin email exists
		if (a.getEmail() != null){
			// Admin email exists
			return Response.status(404).build();
		}
		
		// Save new admin
		sdao.save(admin);
        
        return Response.ok().status(201).entity(admin)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
	
	
	

}

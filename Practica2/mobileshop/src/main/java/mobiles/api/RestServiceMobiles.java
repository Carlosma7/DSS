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

import mobiles.dao.MobilesDAO;
import mobiles.models.Mobile;

@Path(value = "/mobiles")
public class RestServiceMobiles {
	
	String msg = null;
	
	// Get list of all mobiles
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getData() {
		List<Mobile> msg = new MobilesDAO().getListMobiles();
		// Return list of mobiles
		return Response.status(Status.OK).entity(msg)
				.header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
				.build();
	}
	
	// Delete a mobile by ID
	@DELETE
    @Path("/{id}")
    @Consumes("application/json")
    public Response deleteMobile(@PathParam("id") String mobileId) {
		MobilesDAO sdao = new MobilesDAO();
		// Remove mobile
		sdao.remove(Long.parseLong(mobileId));
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
	
	// Create mobile
	@POST
	@Path("/")
	@Consumes("application/json")
	public Response createMobile(Mobile mobile) {
		MobilesDAO sdao = new MobilesDAO();
		// Save mobile
		sdao.save(mobile);
        
        return Response.ok().entity(mobile)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
    }
	
	
	

}

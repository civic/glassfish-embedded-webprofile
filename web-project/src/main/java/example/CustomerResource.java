package example;

import example.entity.Customer;
import example.service.CustomerService;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 */
@Path("customers")
@RequestScoped
public class CustomerResource {
    @Inject
    private CustomerService cs;


    /**
     * 静的にJSONを返すだけ
     */
    @GET
    @Path("hello")
    @Produces("application/json")
    public JsonObject getJson() {
        return Json.createObjectBuilder().add("msg", "Hello World").build();
    }

    /**
     * Customerの一覧を返す
     * @return 
     */
    @GET
    @Produces("application/json")
    public List<Customer> getCustomers() {
        return cs.all();

    }
}

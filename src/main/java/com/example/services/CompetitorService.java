
package com.example.services;

import com.example.PersistenceManager;
import com.example.models.Competitor;
import com.example.models.CompetitorDTO;
import com.example.models.Producto;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;


@Path("/competitors")
@Produces(MediaType.APPLICATION_JSON)
public class CompetitorService {

    @PersistenceContext(unitName = "monPu")
    EntityManager entityManager;

    @PostConstruct
    public void init() {
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        Query q = entityManager.createQuery("select u from Competitor u order by u.surname ASC");
        List<Competitor> competitors = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(competitors).build();
    }
    
    @GET
    @Path("/get/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParams(@PathParam ("name")String name){
        
        TypedQuery<Competitor>query =(TypedQuery<Competitor>)entityManager.createQuery("SELECT c FROM Competitor c WHERE c.name = :name");
        List<Competitor>competitors =query.setParameter("name", name).getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(competitors).build();
    }
    
    @GET
    @Path("/getallProduc")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProduc() {
        
        TypedQuery<Producto>query =(TypedQuery<Producto>)entityManager.createQuery("SELECT u.name FROM Producto u WHERE name LIKE a%");
        List<Producto> pro= query.getResultList();
        
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(pro).build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCompetitor(CompetitorDTO competitor) {

        Competitor c = new Competitor();
        JSONObject rta = new JSONObject();
        c.setAddress(competitor.getAddress());
        c.setAge(competitor.getAge());
        c.setCellphone(competitor.getCellphone());
        c.setCity(competitor.getCity());
        c.setCountry(competitor.getCountry());
        c.setName(competitor.getName());
        c.setSurname(competitor.getSurname());
        c.setTelephone(competitor.getTelephone());

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(c);
            entityManager.getTransaction().commit();
            entityManager.refresh(c);
            rta.put("competitor_id", c.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                c = null;
            }
        } finally {
        	entityManager.clear();
        	entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    }
    
    @POST
    @Path("/vehicle")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCompetitorWhithVehicle(CompetitorDTO competitor) {

        Competitor c = new Competitor();
        JSONObject rta = new JSONObject();
        c.setAddress(competitor.getAddress());
        c.setAge(competitor.getAge());
        c.setCellphone(competitor.getCellphone());
        c.setCity(competitor.getCity());
        c.setCountry(competitor.getCountry());
        c.setName(competitor.getName());
        c.setSurname(competitor.getSurname());
        c.setTelephone(competitor.getTelephone());
        c.setVehicle(competitor.getVehicle());

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(c);
            entityManager.getTransaction().commit();
            entityManager.refresh(c);
            rta.put("competitor_id", c.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                c = null;
            }
        } finally {
        	entityManager.clear();
        	entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    }
    
    @OPTIONS
    public Response cors(@javax.ws.rs.core.Context HttpHeaders requestHeaders) {
        return Response.status(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS").header("Access-Control-Allow-Headers", "AUTHORIZATION, content-type, accept").build();
    }

}

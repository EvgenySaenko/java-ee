package ru.geekbrains.rest;

import ru.geekbrains.persist.dto.CustomerDto;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Local
@Path("/v1/customer")
public interface CustomerResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<CustomerDto> findAll();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    CustomerDto findById(@PathParam("id") Long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void insert(CustomerDto customerDto);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    void update(CustomerDto customerDto);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Long id);
}

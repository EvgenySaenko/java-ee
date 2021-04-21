package ru.geekbrains.rest;

import ru.geekbrains.persist.dto.ProductDto;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Local
@Path("/v1/product")
public interface ProductResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<ProductDto> findAll();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    ProductDto findById(@PathParam("id") long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void insert(ProductDto productDto);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    void update(ProductDto productDto);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Long id);
}

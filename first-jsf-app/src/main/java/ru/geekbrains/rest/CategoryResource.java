package ru.geekbrains.rest;

import ru.geekbrains.persist.dto.CategoryDto;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Local
@Path("/v1/category")
public interface CategoryResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<CategoryDto> findAll();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    CategoryDto findById(@PathParam("id") Long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void insert(CategoryDto categoryDto);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    void update(CategoryDto categoryDto);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Long id);
}

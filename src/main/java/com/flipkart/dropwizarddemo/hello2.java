package com.flipkart.dropwizarddemo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/hello2/{name}")
public class hello2 {

    @GET
    public String HelloWorld(@PathParam("name") String name)
    {
        return "Hello "+name+"!";
    }

}

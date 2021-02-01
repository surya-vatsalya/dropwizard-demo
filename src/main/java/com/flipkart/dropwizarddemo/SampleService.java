package com.flipkart.dropwizarddemo;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import com.flipkart.dropwizarddemo.HelloWorldResource;


public class SampleService extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new SampleService().run(args);
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        HelloWorldResource resource = new HelloWorldResource();
        environment.jersey().register(resource);

        StudentController studentController = new StudentController();
        environment.jersey().register(studentController);

        AdminController adminController = new AdminController();
        environment.jersey().register(adminController);

        ProfessorController professorController = new ProfessorController();
        environment.jersey().register(professorController);

    }
}

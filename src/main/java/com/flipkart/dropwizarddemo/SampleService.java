package com.flipkart.dropwizarddemo;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import com.flipkart.dropwizarddemo.HelloWorldResource;


public class SampleService extends Application<SampleCongiguarion> {
    public static void main(String[] args) throws Exception {
        new SampleService().run(args);
    }

    @Override
    public void run(SampleCongiguarion configuration, Environment environment) throws Exception {
        HelloWorldResource resource = new HelloWorldResource();
        environment.jersey().register(resource);
        hello2 re = new hello2();
        environment.jersey().register(re);
    }
}

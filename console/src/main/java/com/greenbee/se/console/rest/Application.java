package com.greenbee.se.console.rest;

import org.apache.commons.lang3.ClassUtils;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class Application extends ResourceConfig {

    public Application() {
        registerClasses(JacksonFeature.class);
        packages(ClassUtils.getPackageName(this.getClass()));
    }

}

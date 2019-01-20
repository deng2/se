package com.vitria.test.rs.ws;

//import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

//@ApplicationPath("/rest")
public class Application extends ResourceConfig {

    public Application() {
        registerClasses(JacksonFeature.class);
        packages("com.vitria.test.rs.ws");
    }

}

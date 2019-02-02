// Copyright (c) 2015 Vitria Technology, Inc.
// All Rights Reserved.
//
package com.greenbee.se.start;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Properties;

import com.greenbee.commons.ClasspathLib;
import com.greenbee.commons.FileLib;
import com.greenbee.commons.ReflectionLib;
import com.greenbee.commons.ResourceLoaderUtil;

public class JettyMain {
    public static final String SYS_SE_HOME = "se.home";
    public static final String SYS_JETTY_HOME = "jetty.home";
    public static final String SYS_JETTY_BASE = "jetty.base";
    public static final String SE_PROPERTY_JETTYCLASS = "jetty.class";
    public static final String SE_PROPERTY_JETTYXML = "jetty.xml";
    public static final String SE_HOME = "SE_HOME";
    public static final String SE_PROPERTIES = "se_conf.properties";
    private Properties settings_;
    private String home_;

    public static void main(String[] args) throws Exception {
        JettyMain main = new JettyMain();
        main.run();
    }

    protected void init() throws IOException {
        home_ = System.getenv(SE_HOME);
        home_ = new File(home_).getAbsolutePath();
        System.setProperty(SYS_JETTY_HOME, home_);
        System.setProperty(SYS_JETTY_BASE, home_);
        System.setProperty(SYS_SE_HOME, home_);
        ClassLoader cl = JettyMain.class.getClassLoader();
        settings_ = ResourceLoaderUtil.loadProperties(cl, SE_PROPERTIES);
    }

    protected void setupLog4j() throws Exception {
        //setup log4j
        String clazz = "org.apache.log4j.xml.DOMConfigurator";
        String method = "configureAndWatch";
        Class<?>[] paramTypes = new Class<?>[] { String.class, long.class };
        Object[] paramValues = new Object[] { FileLib.join(home_, "etc/log4j.xml"), 30L };
        ReflectionLib.invokeStatic(null, clazz, method, paramTypes, paramValues);
    }

    @SuppressWarnings({ "rawtypes" })
    protected void startJettyServer() throws Exception {
        //like etc/jetty.xml,etc/jetty_extend.xml
        String jettyXML = settings_.getProperty(SE_PROPERTY_JETTYXML);
        List<String> apaths = ClasspathLib.split(jettyXML);
        for (int i = 0; i < apaths.size(); i++) {
            String path = apaths.get(i);
            apaths.set(i, FileLib.join(home_, path));
        }
        String jettyClazz = settings_.getProperty(SE_PROPERTY_JETTYCLASS);
        String[] args = apaths.toArray(new String[0]);
        Class[] paramTypes = new Class[] { args.getClass() };
        Object[] params = new Object[] { args };
        ReflectionLib.invokeStatic(JettyMain.class.getClassLoader(), jettyClazz,
                                   "main", paramTypes, params);
    }

    public void run() throws Exception {
        init();
        setupLog4j();
        startJettyServer();
    }
}

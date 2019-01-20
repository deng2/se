// Copyright (c) 2016 Vitria Technology, Inc.
// All Rights Reserved.
//
package com.vitria.test.tapestry.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

public class DetailLayout {
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String name;

    @SetupRender
    void init() {
    }

}

// Copyright (c) 2016 Vitria Technology, Inc.
// All Rights Reserved.
//
package com.vitria.test.tapestry.components;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

@Import(
        stylesheet = { 
                "context:css/bootstrap.min.css", 
                "context:css/sb-admin.css", 
                "context:css/plugins/morris.css",
                "context:font-awesome/css/font-awesome.min.css" 
        }/*,
        library={
                "context:js/jquery.js",
                "context:js/bootstrap.min.js",
                "context:js/plugins/morris/raphael.min.js",
                "context:js/plugins/morris/morris.min.js",
                "context:js/plugins/morris/morris-data.js"
        }*/
)
public class CommonLayout {
    @Property
    @Inject
    @Symbol(SymbolConstants.CONTEXT_PATH)
    private String context;
}

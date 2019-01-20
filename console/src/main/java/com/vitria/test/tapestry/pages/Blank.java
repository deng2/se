// Copyright (c) 2016 Vitria Technology, Inc.
// All Rights Reserved.
//
package com.vitria.test.tapestry.pages;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry5.EventContext;

public class Blank {
    private static final Log log_ = LogFactory.getLog(Blank.class);

    protected void onActivate(EventContext ctx) {
        if (log_.isDebugEnabled()) {
            String[] values = ctx.toStrings();
            log_.debug(Arrays.asList(values));
        }
    }
}

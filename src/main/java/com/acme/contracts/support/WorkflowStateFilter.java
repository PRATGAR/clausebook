package com.acme.contracts.support;

import java.io.ObjectInputFilter;

public final class WorkflowStateFilter {

    public static final ObjectInputFilter ALLOWED = ObjectInputFilter.Config.createFilter(
            "maxdepth=6;maxrefs=2000;"
                    + "com.acme.contracts.model.WorkflowState;"
                    + "com.acme.contracts.model.ApprovalStatus;"
                    + "java.lang.Enum;java.lang.String;java.util.ArrayList;"
                    + "java.time.Ser;java.time.Instant;!*");

    private WorkflowStateFilter() {
    }
}

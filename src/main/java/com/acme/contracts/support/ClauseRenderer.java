package com.acme.contracts.support;

import com.acme.contracts.model.Clause;
import java.io.StringWriter;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Component;

@Component
public class ClauseRenderer {

    static {
        Velocity.init();
    }

    public String render(Clause clause, Map<String, Object> variables) {
        VelocityContext context = new VelocityContext(variables);
        StringWriter out = new StringWriter();
        Velocity.evaluate(context, out, "clause-" + clause.id(), clause.text());
        return out.toString();
    }
}

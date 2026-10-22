package com.acme.contracts.support;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TemplateRenderer {

    private final PebbleEngine engine;

    public TemplateRenderer(PebbleEngine engine) {
        this.engine = engine;
    }

    public String renderBody(String templateBody, Map<String, Object> variables) throws IOException {
        PebbleTemplate compiled = engine.getLiteralTemplate(templateBody);
        StringWriter out = new StringWriter();
        compiled.evaluate(out, variables);
        return out.toString();
    }

    public String renderContractPage(Map<String, Object> variables) throws IOException {
        PebbleTemplate page = engine.getLiteralTemplate(ShellMarkup.CONTRACT_PAGE);
        StringWriter out = new StringWriter();
        page.evaluate(out, variables);
        return out.toString();
    }
}

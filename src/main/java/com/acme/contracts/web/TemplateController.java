package com.acme.contracts.web;

import com.acme.contracts.model.Template;
import com.acme.contracts.service.TemplateService;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {

    private final TemplateService templates;

    public TemplateController(TemplateService templates) {
        this.templates = templates;
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public Template importPackage(@RequestBody String packageXml) throws Exception {
        return templates.importPackage(packageXml);
    }

    @PostMapping("/{id}/preview")
    public Map<String, String> preview(@PathVariable long id, @RequestBody Map<String, Object> variables)
            throws Exception {
        return Map.of("rendered", templates.preview(id, variables));
    }
}

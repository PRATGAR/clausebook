package com.acme.contracts.service;

import com.acme.contracts.model.Template;
import com.acme.contracts.repo.TemplateRepository;
import com.acme.contracts.support.NotFoundException;
import com.acme.contracts.support.TemplateRenderer;
import com.acme.contracts.support.XmlText;
import java.io.StringReader;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

@Service
public class TemplateService {

    private final DocumentBuilderFactory packageFactory;
    private final TemplateRepository templates;
    private final TemplateRenderer renderer;

    public TemplateService(@Qualifier("templatePackageXmlFactory") DocumentBuilderFactory packageFactory,
                           TemplateRepository templates, TemplateRenderer renderer) {
        this.packageFactory = packageFactory;
        this.templates = templates;
        this.renderer = renderer;
    }

    public Template importPackage(String xml) throws Exception {
        DocumentBuilder builder = packageFactory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        Element root = document.getDocumentElement();
        String name = root.getAttribute("name");
        String category = root.getAttribute("category");
        String body = XmlText.child(root, "body");
        long id = templates.insert(name, body, category);
        return new Template(id, name, body, category);
    }

    public String preview(long id, Map<String, Object> variables) throws Exception {
        Template template = templates.find(id).orElseThrow(() -> new NotFoundException("template", id));
        return renderer.renderBody(template.body(), variables);
    }
}

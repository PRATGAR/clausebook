package com.acme.contracts.service;

import com.acme.contracts.model.DirectoryEntry;
import com.acme.contracts.model.NewSigner;
import com.acme.contracts.model.Signer;
import com.acme.contracts.repo.SignerRepository;
import com.acme.contracts.support.NotFoundException;
import com.acme.contracts.support.XmlText;
import java.io.StringReader;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Service
public class SignerService {

    private final DocumentBuilderFactory rosterFactory;
    private final SignerRepository signers;
    private final SignerDirectory directory;

    public SignerService(@Qualifier("rosterXmlFactory") DocumentBuilderFactory rosterFactory,
                         SignerRepository signers, SignerDirectory directory) {
        this.rosterFactory = rosterFactory;
        this.signers = signers;
        this.directory = directory;
    }

    public Signer create(NewSigner request) {
        long id = signers.insert(request.displayName(), request.email());
        return require(id);
    }

    public int importRoster(String xml) throws Exception {
        DocumentBuilder builder = rosterFactory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        NodeList nodes = document.getElementsByTagName("signer");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element entry = (Element) nodes.item(i);
            signers.insert(XmlText.child(entry, "name"), XmlText.child(entry, "email"));
        }
        return nodes.getLength();
    }

    public Signer require(long id) {
        return signers.find(id).orElseThrow(() -> new NotFoundException("signer", id));
    }

    public List<DirectoryEntry> directoryEntry(long id) throws Exception {
        Signer signer = require(id);
        return directory.findByName(signer.displayName());
    }
}

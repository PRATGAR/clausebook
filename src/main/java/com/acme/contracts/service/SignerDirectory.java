package com.acme.contracts.service;

import com.acme.contracts.model.DirectoryEntry;
import com.acme.contracts.support.DirectoryClient;
import com.acme.contracts.support.DirectoryFilters;
import java.util.List;
import javax.naming.NamingException;
import org.springframework.stereotype.Service;

@Service
public class SignerDirectory {

    private final DirectoryClient client;

    public SignerDirectory(DirectoryClient client) {
        this.client = client;
    }

    public List<DirectoryEntry> findByName(String name) throws NamingException {
        return client.searchPeople(DirectoryFilters.commonName(name));
    }
}

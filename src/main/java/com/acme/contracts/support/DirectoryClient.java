package com.acme.contracts.support;

import com.acme.contracts.config.AppProperties;
import com.acme.contracts.model.DirectoryEntry;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.springframework.stereotype.Component;

@Component
public class DirectoryClient {

    private static final String[] RETURNED = {"cn", "mail", "departmentNumber"};

    private final AppProperties props;

    public DirectoryClient(AppProperties props) {
        this.props = props;
    }

    public List<DirectoryEntry> searchPeople(String filter) throws NamingException {
        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, props.ldap().url());
        DirContext context = new InitialDirContext(env);
        try {
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            controls.setReturningAttributes(RETURNED);
            NamingEnumeration<SearchResult> results = context.search(props.ldap().baseDn(), filter, controls);
            List<DirectoryEntry> entries = new ArrayList<>();
            while (results.hasMore()) {
                SearchResult hit = results.next();
                entries.add(toEntry(hit));
            }
            return entries;
        } finally {
            context.close();
        }
    }

    private DirectoryEntry toEntry(SearchResult hit) throws NamingException {
        Attributes attrs = hit.getAttributes();
        return new DirectoryEntry(hit.getNameInNamespace(), read(attrs, "cn"), read(attrs, "mail"),
                read(attrs, "departmentNumber"));
    }

    private String read(Attributes attrs, String name) throws NamingException {
        return attrs.get(name) == null ? null : String.valueOf(attrs.get(name).get());
    }
}

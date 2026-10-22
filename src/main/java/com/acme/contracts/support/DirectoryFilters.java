package com.acme.contracts.support;

public final class DirectoryFilters {

    private static final String PERSON = "(objectClass=inetOrgPerson)";

    private DirectoryFilters() {
    }

    public static String commonName(String name) {
        return "(&" + PERSON + "(cn=" + name + "))";
    }
}

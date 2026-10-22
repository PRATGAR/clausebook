package com.acme.contracts.repo;

final class TextMatch {

    private TextMatch() {
    }

    static String contains(String column, String term) {
        String literal = term.replace("%", "\\%").replace("_", "\\_");
        return column + " LIKE '%" + literal + "%'";
    }
}

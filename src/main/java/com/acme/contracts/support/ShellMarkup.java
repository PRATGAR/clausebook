package com.acme.contracts.support;

public final class ShellMarkup {

    public static final String CONTRACT_PAGE = """
            <html>
            <head><meta charset="utf-8"><title>{{ title }}</title></head>
            <body>
            <h1>{{ title }}</h1>
            <h2>Between us and {{ counterparty }}</h2>
            <div class="body">{{ body }}</div>
            </body>
            </html>
            """;

    private ShellMarkup() {
    }
}

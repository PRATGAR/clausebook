package com.acme.contracts.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class PdfRenderer {

    public int run(List<String> command) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
        try (InputStream output = process.getInputStream()) {
            output.transferTo(OutputStream.nullOutputStream());
        }
        if (!process.waitFor(60, TimeUnit.SECONDS)) {
            process.destroyForcibly();
            throw new IllegalStateException("renderer timed out");
        }
        return process.exitValue();
    }
}

package com.acme.contracts.repo;

import com.acme.contracts.config.AppProperties;
import com.acme.contracts.model.ExhibitKey;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import org.springframework.stereotype.Repository;

@Repository
public class AttachmentStore {

    private final AppProperties props;

    public AttachmentStore(AppProperties props) {
        this.props = props;
    }

    public byte[] readExhibit(ExhibitKey key) throws IOException {
        Path file = props.dirs().exhibitRoot().resolve(key.fileName());
        try (InputStream in = new FileInputStream(file.toFile())) {
            return in.readAllBytes();
        }
    }
}

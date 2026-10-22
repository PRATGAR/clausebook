package com.acme.contracts.support;

import com.acme.contracts.config.AppProperties;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

@Component
public class VersionLayout {

    private final AppProperties props;

    public VersionLayout(AppProperties props) {
        this.props = props;
    }

    public Path versionDir(String storageLabel) {
        return props.dirs().storageRoot().resolve(storageLabel);
    }

    public Path versionFile(String storageLabel, int versionNo) {
        return versionDir(storageLabel).resolve("v" + versionNo + ".txt");
    }
}

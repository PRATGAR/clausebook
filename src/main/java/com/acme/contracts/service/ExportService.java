package com.acme.contracts.service;

import com.acme.contracts.config.AppProperties;
import com.acme.contracts.model.Contract;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;

@Service
public class ExportService {

    private final ContractService contracts;
    private final AppProperties props;

    public ExportService(ContractService contracts, AppProperties props) {
        this.contracts = contracts;
        this.props = props;
    }

    public Path exportSigned(long contractId, String folder) throws IOException {
        Contract contract = contracts.require(contractId);
        Path source = props.dirs().signedRoot().resolve(contract.id() + ".pdf");
        Path destination = props.dirs().exportRoot().resolve(folder);
        return copyInto(source, destination);
    }

    private Path copyInto(Path source, Path destinationDir) throws IOException {
        Files.createDirectories(destinationDir);
        return Files.copy(source, destinationDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
    }
}

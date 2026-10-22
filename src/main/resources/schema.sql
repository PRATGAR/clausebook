CREATE SEQUENCE employee_seq START WITH 70000;

CREATE TABLE templates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    body VARCHAR(100000) NOT NULL,
    category VARCHAR(80)
);

CREATE TABLE clauses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    text VARCHAR(4000) NOT NULL
);

CREATE TABLE signers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    display_name VARCHAR(400) NOT NULL,
    email VARCHAR(400) NOT NULL,
    employee_id BIGINT DEFAULT NEXT VALUE FOR employee_seq
);

CREATE TABLE contracts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(300) NOT NULL,
    counterparty VARCHAR(300) NOT NULL,
    storage_label VARCHAR(300) NOT NULL,
    webhook_url VARCHAR(1000),
    status VARCHAR(20) NOT NULL,
    template_id BIGINT
);

CREATE TABLE contract_versions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contract_id BIGINT NOT NULL,
    version_no INT NOT NULL,
    file_path VARCHAR(1000) NOT NULL
);

CREATE TABLE approvals (
    contract_id BIGINT PRIMARY KEY,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE checkpoints (
    contract_id BIGINT PRIMARY KEY,
    state_blob VARBINARY(1000000) NOT NULL
);

CREATE TABLE reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    filter_expr VARCHAR(2000) NOT NULL,
    sort_by VARCHAR(40) NOT NULL
);

CREATE TABLE signature_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contract_id BIGINT NOT NULL,
    signer_id BIGINT NOT NULL,
    pin_hash VARCHAR(128) NOT NULL,
    status VARCHAR(20) NOT NULL
);

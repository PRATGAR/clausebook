package com.acme.contracts.model;

public record Contract(long id, String title, String counterparty, String storageLabel,
                       String webhookUrl, ContractStatus status, Long templateId) {
}

package com.example.eventsourcingcqrs.commands.dto;

public record AddNewAccountRequestDTO(
        double initialBalance,
        String currency
) {
}

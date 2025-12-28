package com.example.eventsourcingcqrs.commands.dto;

public record DebitAccountRequestDTO(
        String accountId,
        double amount,
        String currency
) {
}

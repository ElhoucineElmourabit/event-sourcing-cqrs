package com.example.eventsourcingcqrs.commands.dto;

import com.example.eventsourcingcqrs.enums.AccountStatus;

public record UpdateAccountStatusRequestDTO(
        String accountId,
        AccountStatus status
) {
}

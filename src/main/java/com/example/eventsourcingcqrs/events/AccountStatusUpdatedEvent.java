package com.example.eventsourcingcqrs.events;

import com.example.eventsourcingcqrs.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class AccountStatusUpdatedEvent {
    private String accountId;
    private AccountStatus status;
}

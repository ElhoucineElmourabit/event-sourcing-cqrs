package com.example.eventsourcingcqrs.commands.controllers;

import com.example.eventsourcingcqrs.commands.commands.AddAccountCommand;
import com.example.eventsourcingcqrs.commands.commands.CreditAccountCommand;
import com.example.eventsourcingcqrs.commands.commands.DebitAccountCommand;
import com.example.eventsourcingcqrs.commands.commands.UpdateAccountStatusCommand;
import com.example.eventsourcingcqrs.commands.dto.AddNewAccountRequestDTO;
import com.example.eventsourcingcqrs.commands.dto.CreditAccountRequestDTO;
import com.example.eventsourcingcqrs.commands.dto.DebitAccountRequestDTO;
import com.example.eventsourcingcqrs.commands.dto.UpdateAccountStatusRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;

    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/add")
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO request){
        CompletableFuture<String> response  = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));
        return response;
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> response  = commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return response;
    }

    @PutMapping(path = "/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request) {
        CompletableFuture<String> commandResponse = commandGateway.send(
                new DebitAccountCommand(
                        request.accountId(),
                        request.amount(),
                        request.currency()
                ));
        return commandResponse;
    }

    @PutMapping(path = "/updateStatus")
    public CompletableFuture<String> debitAccount(@RequestBody UpdateAccountStatusRequestDTO request) {
        CompletableFuture<String> commandResponse = commandGateway.send(
                new UpdateAccountStatusCommand(
                        request.accountId(),
                        request.status()
                ));
        return commandResponse;
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception){
        return exception.getMessage();
    }
    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
}

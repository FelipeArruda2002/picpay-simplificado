package com.desafio.picpaysimplificado.rest.api.transaction;

import com.desafio.picpaysimplificado.rest.api.transaction.dtos.TransactionDto;
import com.desafio.picpaysimplificado.rest.api.transaction.dtos.TransactionResponseDto;
import com.desafio.picpaysimplificado.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TransactionResponseDto makeTransaction(@RequestBody TransactionDto transactionDto) throws Exception {
        return transactionService.makeTransfer(transactionDto);
    }
}

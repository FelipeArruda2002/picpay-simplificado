package com.desafio.picpaysimplificado.services;

import com.desafio.picpaysimplificado.domain.models.User;
import com.desafio.picpaysimplificado.rest.api.transaction.dtos.TransactionDto;
import com.desafio.picpaysimplificado.rest.api.transaction.dtos.TransactionResponseDto;

public interface TransactionService {

    void validateTransaction(User payer, TransactionDto transaction) throws Exception;

    TransactionResponseDto makeTransfer(TransactionDto transaction) throws Exception;

    boolean authorizeTransfer();
}

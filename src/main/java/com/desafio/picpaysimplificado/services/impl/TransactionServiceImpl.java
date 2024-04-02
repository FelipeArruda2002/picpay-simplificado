package com.desafio.picpaysimplificado.services.impl;

import com.desafio.picpaysimplificado.domain.enums.UserType;
import com.desafio.picpaysimplificado.domain.models.Transaction;
import com.desafio.picpaysimplificado.domain.models.User;
import com.desafio.picpaysimplificado.repositories.TransactionRepository;
import com.desafio.picpaysimplificado.rest.api.transaction.dtos.TransactionDto;
import com.desafio.picpaysimplificado.rest.api.transaction.dtos.TransactionResponseDto;
import com.desafio.picpaysimplificado.services.EmailService;
import com.desafio.picpaysimplificado.services.TransactionService;
import com.desafio.picpaysimplificado.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final UserService userService;
    private final RestTemplate restTemplate;
    private final TransactionRepository repository;
    private final EmailService emailService;

    @Value("${external.authorizer.url}")
    String authorizerUrl;

    public TransactionServiceImpl(UserService userService, RestTemplate restTemplate, TransactionRepository repository, EmailService emailService) {
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.repository = repository;
        this.emailService = emailService;
    }

    @Override
    public void validateTransaction(User payer, TransactionDto transaction) throws Exception {
        if (UserType.SHOPKEEPER.equals(payer.getUserType())) {
            throw new Exception("Lojistas não têm a funcionalidade de realizar transferência.");
        }

        if (payer.getBalance().compareTo(transaction.value()) < 0) {
            throw new Exception("Saldo insuficiente para realizar essa transferência.");
        }
    }

    @Override
    @Transactional
    public TransactionResponseDto makeTransfer(TransactionDto transaction) throws Exception {
        User payer = this.userService.userById(transaction.payer());

        validateTransaction(payer, transaction);

        User payee = this.userService.userById(transaction.payee());

        payer.setBalance(payer.getBalance().subtract(transaction.value()));
        payee.setBalance(payee.getBalance().add(transaction.value()));

        this.userService.saveUser(payer);
        this.userService.saveUser(payee);

        if (!authorizeTransfer()) {
            throw new Exception("Transferência não autorizada.");
        }

        Transaction transactionEntity = createTransaction(transaction, payer, payee);
        this.repository.save(transactionEntity);

        emailService.sendEmail(transactionEntity);

        return new TransactionResponseDto(payer.getName(), payee.getName(), transaction.value(), LocalDate.now());
    }

    private Transaction createTransaction(TransactionDto transaction, User payer, User payee) {
        var transactionEntity = new Transaction();
        transactionEntity.setPayee(payee);
        transactionEntity.setPayer(payer);
        transactionEntity.setAmount(transaction.value());
        transactionEntity.setDateTransaction(LocalDate.now());
        return transactionEntity;
    }

    @Override
    public boolean authorizeTransfer() {
        ResponseEntity<Map> response = restTemplate.getForEntity(authorizerUrl, Map.class);
        if (response != null) {
            Map json = response.getBody();
            String message = (String) json.get("message");
            if ("Autorizado".equalsIgnoreCase(message)) {
                return true;
            }
        }
        return false;
    }

}

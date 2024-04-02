package com.desafio.picpaysimplificado.services;

import com.desafio.picpaysimplificado.domain.models.Transaction;
import com.desafio.picpaysimplificado.domain.models.User;

public interface EmailService {

    void sendEmail(Transaction transaction) throws Exception;

    boolean authorizeNotification();

}

package com.desafio.picpaysimplificado.services.impl;

import com.desafio.picpaysimplificado.domain.models.Transaction;
import com.desafio.picpaysimplificado.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final RestTemplate restTemplate;

    @Value(value = "${spring.mail.username}")
    private String username;

    @Value("${authorizer.mail.url}")
    String authorizerUrl;

    public EmailServiceImpl(JavaMailSender mailSender, RestTemplate restTemplate) {
        this.mailSender = mailSender;
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendEmail(Transaction transaction) throws Exception {
        if (!authorizeNotification()) {
            throw new Exception("Serviço de email indisponível.");
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(transaction.getPayee().getEmail());
            message.setSubject("Pagamento recebido");
            message.setText(String.format("Você recebeu um Pix de: %s \n " +
                            "Valor recebido R$ %.2f \n" +
                            "Detalhes do pagamento \n" +
                            "Data e hora: %s",
                    transaction.getPayer().getName(),
                    transaction.getAmount(),
                    transaction.getDateTransaction().toString()
            ));

            mailSender.send(message);
        } catch (MailException e) {
            throw new Exception("Não foi possível enviar o email");
        }
    }

    @Override
    public boolean authorizeNotification() {
        ResponseEntity<Map> response = restTemplate.getForEntity(authorizerUrl, Map.class);
        Map body = response.getBody();
        Boolean isAuthorized = (Boolean) body.get("message");
        return isAuthorized;
    }

}

package com.desafio.picpaysimplificado.rest.api.transaction.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponseDto(String payerName,
                                     String payeeName,
                                     BigDecimal value,
                                     LocalDate dateTransaction) {
}

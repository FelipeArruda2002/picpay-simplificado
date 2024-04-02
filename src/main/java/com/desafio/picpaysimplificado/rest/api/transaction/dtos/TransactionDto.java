package com.desafio.picpaysimplificado.rest.api.transaction.dtos;

import java.math.BigDecimal;

public record TransactionDto(BigDecimal value,
                             Long payer,
                             Long payee) {
}

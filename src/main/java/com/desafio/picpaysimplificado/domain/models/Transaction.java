package com.desafio.picpaysimplificado.domain.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "payer_id_payer")
    private User payer;
    @ManyToOne
    @JoinColumn(name = "payee_id_payee")
    private User payee;
    private BigDecimal amount;
    private LocalDate dateTransaction;

    public Long getId() {
        return id;
    }

    public User getPayer() {
        return payer;
    }

    public User getPayee() {
        return payee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDateTransaction() {
        return dateTransaction;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public void setPayee(User payee) {
        this.payee = payee;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }
}

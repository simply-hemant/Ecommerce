package com.simply.service;

import com.simply.model.Order;
import com.simply.model.Seller;
import com.simply.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Order order);
    List<Transaction> getTransactionBySeller(Seller seller);
    List<Transaction>getAllTransactions();
}

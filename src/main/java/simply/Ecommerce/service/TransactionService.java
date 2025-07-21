package simply.Ecommerce.service;

import simply.Ecommerce.model.Order;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Order order);
    List<Transaction> getTransactionSeller(Seller seller);
    List<Transaction> getAllTransactions();

}

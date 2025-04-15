package simply.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import simply.Ecommerce.exception.SellerException;
import simply.Ecommerce.model.Order;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.model.Transaction;
import simply.Ecommerce.service.SellerService;
import simply.Ecommerce.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final SellerService sellerService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Order order){

        Transaction transaction = transactionService.createTransaction(order);
        return ResponseEntity.ok(transaction);

    }

    @GetMapping("/seller")
    public ResponseEntity<List<Transaction>> getTransactionSeller(
            @RequestHeader("Authorization") String jwt) throws Exception {

        Seller seller = sellerService.getSellerProfile(jwt);
        List<Transaction> transactions = transactionService.getTransactionSeller(seller);

        return ResponseEntity.ok(transactions);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){

        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

}

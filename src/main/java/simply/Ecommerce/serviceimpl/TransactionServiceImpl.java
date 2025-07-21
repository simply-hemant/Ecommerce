package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simply.Ecommerce.model.Order;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.model.Transaction;
import simply.Ecommerce.repository.SellerRepo;
import simply.Ecommerce.repository.TransactionRepo;
import simply.Ecommerce.service.TransactionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final SellerRepo sellerRepo;

    @Override
    public Transaction createTransaction(Order order) {

        Seller seller = sellerRepo.findById(order.getSellerId()).get();
        Transaction transaction = new Transaction();
        transaction.setCustomer(order.getUser());
        transaction.setOrder(order);
        transaction.setSeller(seller);

        return transactionRepo.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionSeller(Seller seller) {

        return transactionRepo.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransactions() {

        return transactionRepo.findAll();
    }
}

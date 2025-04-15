//package simply.Ecommerce.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import simply.Ecommerce.Enum.PayoutsStatus;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//@Entity
//public class Payouts {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long payoutId;
//
//    @OneToMany
//    private List<Transaction> transactions = new ArrayList<>();
//
//    @ManyToOne
//    private Seller seller;
//
//    private Long amount;
//
//    private PayoutsStatus status = PayoutsStatus.PENDING;
//
//    private LocalDateTime data=LocalDateTime.now();
//}

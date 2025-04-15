package simply.Ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import simply.Ecommerce.Enum.AccountStatus;
import simply.Ecommerce.Enum.USER_ROLE;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String sellerName;

    private String mobile;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Embedded
    private BusinessDetails businessDetails = new BusinessDetails();

    @Embedded
    private BankDetails bankDetails = new BankDetails();

    @OneToOne(cascade = CascadeType.ALL)
    private Address pickupAddress=new Address();

    private String GSTIN;

    private USER_ROLE role;

    private  boolean isEmailVerified=false;

    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;

}

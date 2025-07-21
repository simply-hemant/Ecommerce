<<<<<<< HEAD
    package simply.Ecommerce.model;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonProperty;
    import jakarta.persistence.*;
    import lombok.Data;
    import simply.Ecommerce.Enum.USER_ROLE;

    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @Data
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long userId;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private String password;

        private String email;

        private String firstName;

        private String lastName;

        private String mobile;

        private USER_ROLE role;

        @OneToMany
        private Set<Address> addresses = new HashSet<>();

        @JsonIgnore
        @ManyToMany
        @JoinTable(name = "user_coupons", inverseJoinColumns = @JoinColumn(name = "coupon_id"))
        private Set<Coupon> usedCoupons = new HashSet<>();

    }
=======
package simply.Ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import simply.Ecommerce.Enum.USER_ROLE;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String mobile;

    private USER_ROLE role;

    @OneToMany
    private Set<Address> addresses = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_coupons", inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private Set<Coupon> usedCoupons = new HashSet<>();

}
>>>>>>> 852346b (Added DataInitializationComponent and other updates)

package simply.Ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;


@Entity
@Data
@EqualsAndHashCode
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String name;

    @NotNull(message = "flatNo Name Mandatory")
    @NotBlank(message = "flatNo Name Mandatory")
    private String flatNo;

    @NotNull(message = "Street Name Mandatory")
    @NotBlank(message = "Street Name Mandatory")
    private String street;

    private String address;

    @NotNull(message = "City Name Mandatory")
    @NotBlank(message = "City Name Mandatory")
    private String city;

    @NotNull(message = "State should not Blank")
    @NotBlank(message = "State Name Mandatory")
    private String state;

    @NotNull(message = "pinCode should not Blank")
    @NotBlank(message = "pinCode is Mandatory")
    private String pinCode;

    @NotNull(message = "mobile should not Blank")
    @NotBlank(message = "mobile is Mandatory")
    private String mobile;

}

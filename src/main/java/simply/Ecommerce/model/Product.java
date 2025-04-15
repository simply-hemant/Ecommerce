package simply.Ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String title;

    private String description;

    private int mrpPrice;

    private int sellingPrice;

    private int discountPercent;

    private int quantity;

    private String color;

    @ElementCollection
    private List<String> images =new ArrayList<>();

    private int numRatings;

    @ManyToOne
    private Category category;
    
    @ManyToOne
    private Seller seller;
    
    private LocalDateTime localDateTime;
    
    private String size;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

}

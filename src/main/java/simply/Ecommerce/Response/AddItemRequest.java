package simply.Ecommerce.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {

    private Long productId;
    private String size;
    private int quantity;
    private Integer price;

}

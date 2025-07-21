package simply.Ecommerce.Response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentLinkResponse {

    private String payment_link_url;
    private String payment_link_id;

}

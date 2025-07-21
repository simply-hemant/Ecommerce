package simply.Ecommerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import simply.Ecommerce.Enum.PaymentStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {

	private String paymentId;
	private String razorpayPaymentLinkId;
	private String razorpayPaymentLinkReferenceId;
	private String razorpayPaymentLinkStatus;
	private String razorpayPaymentId​;
	private PaymentStatus status;



}

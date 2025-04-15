package simply.Ecommerce.service;

import com.stripe.param.checkout.SessionCreateParams;
import simply.Ecommerce.model.Home;
import simply.Ecommerce.model.HomeCategory;

import java.util.List;

public interface HomeService {

    Home createHomePageData(List<HomeCategory> categories);
}

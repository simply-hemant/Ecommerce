package simply.Ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simply.Ecommerce.Response.ApiResponse;

@RestController
@RequestMapping("/test")
public class HomeController {

    @GetMapping("/")
    public ApiResponse homeController()
    {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("welcome to the simply buy");
        return apiResponse;
    }

}

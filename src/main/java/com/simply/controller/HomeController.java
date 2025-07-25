package com.simply.controller;

import com.simply.response.ApiResponse;
import com.simply.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        String html = """
        <div style='font-family: Arial; font-size: 16px; padding: 20px;'>
             <strong>Welcome to Simply Buy!</strong><br/><br/>
             Ecommerce Backend : <span style='color:green;'>Service is Live!</span><br/><br/>
             Check GitHub for Postman Documentation:<br/>
            Link :  <a href='https://github.com/simply-hemant/Ecommerce' target='_blank'>
                https://github.com/simply-hemant/ecommerce-backend
            </a>
        </div>
        """;

        return ResponseEntity.ok()
                .header("Content-Type", "text/html")
                .body(html);
    }
}

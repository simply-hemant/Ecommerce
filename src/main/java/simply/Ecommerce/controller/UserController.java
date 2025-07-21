<<<<<<< HEAD
package simply.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simply.Ecommerce.Enum.USER_ROLE;
import simply.Ecommerce.Response.AuthResponse;
import simply.Ecommerce.Response.SignupRequest;
import simply.Ecommerce.model.User;
import simply.Ecommerce.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization")String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        return ResponseEntity.ok(user);
    }


}
=======
package simply.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simply.Ecommerce.Enum.USER_ROLE;
import simply.Ecommerce.Response.AuthResponse;
import simply.Ecommerce.Response.SignupRequest;
import simply.Ecommerce.model.User;
import simply.Ecommerce.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization")String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        return ResponseEntity.ok(user);
    }

}
>>>>>>> 852346b (Added DataInitializationComponent and other updates)

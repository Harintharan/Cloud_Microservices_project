package com.ownproject.userservice.controller;

//import com.ownproject.userservice.exception.UserException;
import com.ownproject.userservice.exception.UserNotFoundException;
import com.ownproject.userservice.model.User;
import com.ownproject.userservice.repository.UserRepository;
import com.ownproject.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
private  final UserService userService;

@PostMapping("/users")
public ResponseEntity<User> createUser(@RequestBody @Valid User user){

    User createdUser = userService.createUser(user);

    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);


}

@GetMapping("/users")

    public ResponseEntity <List<User>> getAllUsers()
{

    List<User> users = userService.getAllUsers();
    return new ResponseEntity<>(users, HttpStatus.OK);

}

@GetMapping("/users/{id}")

    public ResponseEntity<User> getUserById( @PathVariable Long id) throws Exception {

    User user = userService.getUserById(id);

    return new ResponseEntity<>(user,HttpStatus.OK);

}



    @PutMapping("/users/{id}")

    public ResponseEntity<User>  updateUserById( @PathVariable Long id , @RequestBody @Valid User user) throws Exception {

        User UupdatedUser = userService.updateUser(id,user);

        return new ResponseEntity<>(user,HttpStatus.OK);

    }


    @DeleteMapping("/users/{id}")

    public ResponseEntity<String> deleteUserById( @PathVariable Long id) throws Exception {

    userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }


    @GetMapping("/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {


        User user = userService.getUserByFromJwt(jwt);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }




}

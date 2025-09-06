package com.ownproject.userservice.service.impl;

import com.ownproject.userservice.exception.UserNotFoundException;
import com.ownproject.userservice.model.User;
import com.ownproject.userservice.payload.response.dto.KeycloakUserDTO;
import com.ownproject.userservice.repository.UserRepository;
import com.ownproject.userservice.service.KeycloakService;
import com.ownproject.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {



        Optional<User> otp =userRepository.findById(id);
        if(otp.isPresent()){
            return otp.get();
        }

        throw new UserNotFoundException("User not found1");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String deleteUser(Long id) {

        Optional<User> otp =userRepository.findById(id);
        if(otp.isPresent()){
            userRepository.delete(otp.get());
            return "User is  deleted";
        }

        throw new UserNotFoundException("User not found");

    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> otp =userRepository.findById(id);
        if(otp.isEmpty()){

            throw new UserNotFoundException("User not found with id" + id);



        }

        User existingUser = otp.get();

        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());
        existingUser.setUserName(user.getUserName());

        return userRepository.save(existingUser);




    }

    @Override
    public User getUserByFromJwt(String jwt) throws Exception {
        KeycloakUserDTO keycloakUserDTO =keycloakService.fetchUserProfileByJwt(jwt);
        return userRepository.findByEmail(keycloakUserDTO.getEmail());


    }
}

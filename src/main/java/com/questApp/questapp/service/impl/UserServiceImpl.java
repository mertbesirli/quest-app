package com.questApp.questapp.service.impl;

import com.questApp.questapp.repository.UserRepository;
import com.questApp.questapp.service.UserService;
import com.questApp.questapp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public User createUser(User userRequest) {
        return userRepository.save(userRequest);
    }

    @Override
    public User getOneUser(Long userId) {
        Optional<User> result = userRepository.findById(userId);
        if(result.isPresent()){
            return result.get();
        }
        else{
            return null;
        }
    }
    @Override
    public User updateUser(Long userId, User userRequest) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            User foundUser = user.get();
            foundUser.setUserName(userRequest.getUserName());
            foundUser.setPass(userRequest.getPass());
            userRepository.save(foundUser);
            return foundUser;
        }
        else{
            return null;
        }
    }

    @Override
    public void deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        }catch (EmptyResultDataAccessException e){
            System.out.println("User" + userId+" does not exitst"); // if you want, make log.
        }
    }
}

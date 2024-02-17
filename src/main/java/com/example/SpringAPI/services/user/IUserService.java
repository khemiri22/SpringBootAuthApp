package com.example.SpringAPI.services.user;

import com.example.SpringAPI.models.User;

import java.util.List;

public interface IUserService {
    User create(User user);
    List<User> read();
    User update(Long id,User user);
    User delete(Long id);
    User findByEmail(String email);
    User findByPassword(String password);
}

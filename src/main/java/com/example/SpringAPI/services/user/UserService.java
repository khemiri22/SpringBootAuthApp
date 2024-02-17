package com.example.SpringAPI.services.user;

import com.example.SpringAPI.models.User;
import com.example.SpringAPI.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService{
    private final IUserRepository iUserRepository;
    @Override
    public User create(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public List<User> read() {
        return iUserRepository.findAll();
    }

    @Override
    public User update(Long id, User user) {
        return iUserRepository.findById(id).map(u ->{
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            return iUserRepository.save(u);
        }).orElse(null);
    }

    @Override
    public User delete(Long id) {
        Optional<User> existingUserOptional = iUserRepository.findById(id);
        if(existingUserOptional.isPresent()){
            User user = existingUserOptional.get();
            iUserRepository.deleteById(id);
            return user;
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> existingUserOptional = iUserRepository.findByEmail(email);
        return existingUserOptional.orElse(null);
    }

    @Override
    public User findByPassword(String password) {
        Optional<User> existingUserOptional = iUserRepository.findByPassword(password);
        return existingUserOptional.orElse(null);
    }

}

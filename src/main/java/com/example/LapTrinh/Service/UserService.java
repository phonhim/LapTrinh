package com.example.LapTrinh.Service;


import com.example.LapTrinh.Entities.User;
import com.example.LapTrinh.dto.UserDto;


import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);

    List<UserDto> findAllUsers();

}

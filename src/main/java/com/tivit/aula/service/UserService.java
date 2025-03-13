package com.tivit.aula.service;

import com.tivit.aula.builder.UserMapper;
import com.tivit.aula.dto.UserDTO;
import com.tivit.aula.model.User;
import com.tivit.aula.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> findAll() {
        return userMapper.toListDTO(userRepository.findAll());
    }

    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userMapper.toDTO(userRepository.save(user));
    }

    public UserDTO update(UserDTO userDTO) {
        return userRepository.findById(userDTO.getId()).map(user -> {
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            return userMapper.toDTO(userRepository.save(user));
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}

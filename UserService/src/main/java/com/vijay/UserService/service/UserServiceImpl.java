package com.vijay.UserService.service;

import com.vijay.UserService.entity.User;
import com.vijay.UserService.exception.ResourceNotFoundException;
import com.vijay.UserService.helper.Helper;
import com.vijay.UserService.model.PageableResponse;
import com.vijay.UserService.model.UserDto;
import com.vijay.UserService.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements UserServce{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("User service Called.. !!");
        User user = mapper.map(userDto, User.class);
        String id = UUID.randomUUID().toString();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setRoles(userDto.getRoles());
        user.setUserId(id);
        userRepository.save(user);
        return mapper.map(user,UserDto.class);
    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber,int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<User> pageUser = userRepository.findAll(pageable);
        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(pageUser, UserDto.class);
        return pageableResponse;
    }

    @Override
    public UserDto findUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!"));
        return mapper.map(user,UserDto.class);
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        user.setGender(userDto.getGender());
        userRepository.save(user);
        return mapper.map(user,UserDto.class);
    }

    @Override
    public void deleteUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!"));
        //images/user/abc.png    //delete user profile image
        String fullPath = imagePath + user.getImageName();

        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            log.info("User image not found in folder");
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        userRepository.delete(user);
        mapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given email id !!"));
        return mapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String keywords) {
        List<User> users=userRepository.findByNameContaining(keywords);
        return users.stream().map(user -> mapper.map(user,UserDto.class) ).collect(Collectors.toList());
    }


}

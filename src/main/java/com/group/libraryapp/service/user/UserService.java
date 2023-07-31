package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //생성
    public void saveUser(UserCreateRequest request){
        userRepository.saveUser(request.getName(), request.getAge());
    }

    //조회
    public List<UserResponse> getUser(){
        return userRepository.getUsers();
    }

    //수정
    public void updateUser(UserUpdateRequest request){
        if(userRepository.isUserNotExist(request.getId())){
            throw new IllegalArgumentException();
        }
        userRepository.updateUser(request.getName(), request.getId());
    }

    //삭제
    public void deleteUser(String name){
        if(userRepository.isUserNotExist(name)){
            throw new IllegalArgumentException();
        }
        userRepository.deleteUser(name);
    }
}

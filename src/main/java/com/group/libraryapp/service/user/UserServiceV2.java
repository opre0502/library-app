package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveUser(UserCreateRequest request){
        User u = userRepository.save(new User(request.getName(), request.getAge()));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public void updateUser(UserUpdateRequest request){
        User user = userRepository.findById(request.getId()).orElseThrow(IllegalAccessError::new);

        user.updateName(request.getName()); //객체를 업데이트
//        userRepository.save(user);    //@Transactional 을 사용해 영속성 컨텍스트가 적용되어 (Dirty Check) 굳이 save를 사용하지 않아도 자동 저장된다.
    }
    @Transactional
    public void deleteUser(String name){
        User user = userRepository.findByName(name).orElseThrow(IllegalAccessError::new);
        userRepository.delete(user);
//        if(userRepository.existsByName(name)){
//            throw new IllegalArgumentException();
//        }
//
//        User user = userRepository.findByName(name);
//        userRepository.delete(user);
    }
}

package com.example.ManagementSystem.service;

import com.example.ManagementSystem.model.User;
import com.example.ManagementSystem.repositories.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //    public Long _count(){
//
//    }
    public Slice<User> findAll() {
        long firstBatch = userRepository.count();
        Slice<User> dd = userRepository.findAll(CassandraPageRequest.first(10));
        System.out.println(firstBatch);
        assertThat(dd).hasSize(10);

        return userRepository.findAll(dd.nextPageable());
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void destroy(UUID id) {
        userRepository.deleteById(id);
    }
}

package com.programatriz.users.repository;

import com.programatriz.users.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,String> {
    User findByEmail(String email);
    List<User> findTop10ByOrderByIdDesc();
}

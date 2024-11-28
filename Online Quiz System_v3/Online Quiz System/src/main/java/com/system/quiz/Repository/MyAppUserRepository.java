package com.system.quiz.Repository;

import java.util.Optional;
import com.system.quiz.Model.MyAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyAppUserRepository extends JpaRepository<MyAppUser, Long>{
    Optional<MyAppUser> findByUsername(String username);
}

package com.kiwoom.app.session.repository;

import com.kiwoom.app.session.entity.UsrMng;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsrMngRepository extends JpaRepository<UsrMng, String> {

    Optional<UsrMng> findByUserIdAndPwd(@Size(max = 20) String userId, @Size(max = 20) String pwd);
}

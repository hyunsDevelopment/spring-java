package com.foresys.app1.system.user.repository;

import com.foresys.app1.system.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
	
}

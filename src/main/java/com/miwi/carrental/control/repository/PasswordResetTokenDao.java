package com.miwi.carrental.control.repository;

import com.miwi.carrental.models.entity.PasswordResetToken;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenDao extends GenericDao<PasswordResetToken>{

  Optional<PasswordResetToken> findByToken(String token);
}

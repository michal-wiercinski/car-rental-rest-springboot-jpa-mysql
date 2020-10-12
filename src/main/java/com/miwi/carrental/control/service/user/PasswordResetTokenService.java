package com.miwi.carrental.control.service.user;

import com.miwi.carrental.control.exception.MyResourceNotFoundException;
import com.miwi.carrental.control.repository.PasswordResetTokenDao;
import com.miwi.carrental.control.service.generic.GenericService;
import com.miwi.carrental.models.entity.PasswordResetToken;
import com.miwi.carrental.models.entity.User;
import java.util.Calendar;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PasswordResetTokenService extends GenericService<PasswordResetToken> {

  private final PasswordResetTokenDao passwordResetTokenDao;

  public PasswordResetTokenService(
      PasswordResetTokenDao passwordResetTokenDao) {
    this.passwordResetTokenDao = passwordResetTokenDao;
  }


  public PasswordResetToken findByToken(String token) {
    try {
      return checkFound(passwordResetTokenDao.findByToken(token));
    } catch (MyResourceNotFoundException ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "The password reset token with token: " + token + " was not found",
          ex);
    }
  }

  public void createPasswordResetTokenForUser(User user, String token) {
    PasswordResetToken resetToken = new PasswordResetToken(token, user);

    passwordResetTokenDao.save(resetToken);
  }

  public String validatePasswordResetToken(String token, String tokenFromPasswordDto) {
    final Optional<PasswordResetToken> passwordResetToken = passwordResetTokenDao
        .findByToken(token);

    return passwordResetToken.isEmpty() || !passwordResetToken.get().getToken()
        .equals(tokenFromPasswordDto) ? "invalidToken"
        : isTokenExpired(passwordResetToken.get()) ? "expired"
            : null;
  }


  private boolean isTokenExpired(PasswordResetToken passToken) {
    final Calendar cal = Calendar.getInstance();
    return passToken.getExpiryDate().before(cal.getTime());
  }
}

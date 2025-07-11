package in.tech_camp.chat_app.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // 暗号化したパスワードを使ってINSERT文実行
  public void createUserWithEncryptedPassword(UserEntity userEntity) {
    String encodedPassword = encodePassword(userEntity.getPassword());
    userEntity.setPassword(encodedPassword);
    userRepository.insert(userEntity);
  }

  // パスワードの暗号化
  public String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }
}

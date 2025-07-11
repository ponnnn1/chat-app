package in.tech_camp.chat_app.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import in.tech_camp.chat_app.entity.UserEntity;

@Mapper
public interface UserRepository {
  // ユーザー追加
  @Insert("INSERT INTO users (name, email, password) VALUES (#{name}, #{email}, #{password})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(UserEntity user);

  // UserAuthenticationServiceで使用する、emailでユーザー情報を取得するメソッドを用意
  @Select("SELECT * FROM users WHERE email = #{email}")
  UserEntity findByEmail(String email);
  
  // ユーザー情報取得（ユーザー情報編集画面）
  @Select("SELECT * FROM users WHERE id = #{id}")
  UserEntity findById(Integer id);

  // ユーザー情報更新
  @Update("UPDATE users SET name = #{name}, email = #{email} WHERE id = #{id}")
  void update(UserEntity user);

  // メールアドレスの重複チェックで用いるSQL
  @Select("SELECT EXISTS(SELECT 1 FROM users WHERE email = #{email})")
  boolean existsByEmail(String email);

  @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email} AND id != #{userId}")
  boolean existsByEmailExcludingCurrent(String email, Integer userId);


  // 新規チャットルーム作成画面のチャットメンバーのプルダウンに表示するユーザー情報（作成するユーザー以外）を取得
  @Select("SELECT * FROM users WHERE id <> #{excludedId}")
  List<UserEntity> findAllExcept(Integer excludedId);
}

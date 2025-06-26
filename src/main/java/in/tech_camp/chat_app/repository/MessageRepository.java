package in.tech_camp.chat_app.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.chat_app.entity.MessageEntity;

@Mapper
public interface MessageRepository {
  // メッセージ情報を保存
  // @Insert("INSERT INTO messages(content, user_id, room_id) VALUES(#{content}, #{user.id}, #{room.id})")
  @Insert("INSERT INTO messages(content, image, user_id, room_id) VALUES(#{content}, #{image}, #{user.id}, #{room.id})")  //画像の保存先パスもINSERT
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(MessageEntity messageEntity);

  // 指定されたルームの全投稿情報を取得（メッセージの表示）
  @Select("SELECT * FROM messages WHERE room_id = #{roomId}")
  @Results(value = {
    @Result(property = "createdAt", column = "created_at"),
    @Result(property = "user", column = "user_id",
            one = @One(select = "in.tech_camp.chat_app.repository.UserRepository.findById"))
  })
  List<MessageEntity> findByRoomId(Integer roomId);

  // 結合テストコードで使用する、テーブル内のメッセージの数を取得するメソッド追加
  // レコードの総数が変化するかを確認するために使用
  @Select("SELECT COUNT(*) FROM messages")
  int count();
}

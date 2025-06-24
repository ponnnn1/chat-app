package in.tech_camp.chat_app.entity;

import java.util.List;

import lombok.Data;

@Data
public class UserEntity {
  private Integer id;
  private String name;
  private String email;
  private String password;
  private List<RoomUserEntity> roomUsers; //RoomUserEntityの情報にアクセスできるようにフィールドを追加
  private List<MessageEntity> messages; //MessageEntityの情報にアクセスできるようにフィールドを追加
}

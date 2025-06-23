package in.tech_camp.chat_app.entity;

import java.util.List;

import lombok.Data;

@Data
public class RoomEntity {
  private Integer id;
  private String name;
  private List<RoomUserEntity> roomUsers; //RoomUserEntityの情報にアクセスできるようにフィールドを追加
  private List<MessageEntity> messages; //MessageEntityの情報にアクセスできるようにフィールドを追加
}

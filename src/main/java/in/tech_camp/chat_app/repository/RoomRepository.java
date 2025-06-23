package in.tech_camp.chat_app.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.chat_app.entity.RoomEntity;

@Mapper
public interface RoomRepository {
  // チャットルーム保存機能
  @Insert("INSERT INTO rooms(name) VALUES(#{name})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(RoomEntity roomEntity);

  // チャットルーム情報取得
  @Select("SELECT * FROM rooms WHERE id = #{id}")
  RoomEntity findById(Integer id);
}

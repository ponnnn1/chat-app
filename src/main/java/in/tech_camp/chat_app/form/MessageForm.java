package in.tech_camp.chat_app.form;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MessageForm {
  // @NotBlank(message = "Name can't be blank",groups = ValidationPriority1.class)
  private String content;

  // 画像用のフィールドを追加
  private MultipartFile image;

  // テキストまたは画像のどちらかが入力されている場合は送信できるよう設定
  public void validateMessage(BindingResult result) {
    if ((content == null || content.isEmpty()) && (image == null || image.isEmpty())) {
      result.rejectValue("Content", "error.Message", "Please enter either content or image");
    }
  }
}

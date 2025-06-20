package in.tech_camp.chat_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.form.LoginForm;
import in.tech_camp.chat_app.form.UserEditForm;
import in.tech_camp.chat_app.form.UserForm;
import in.tech_camp.chat_app.repository.UserRepository;
import in.tech_camp.chat_app.service.UserService;
import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
public class UserController {

  private final UserRepository userRepository;

  private final UserService userService;

  @GetMapping("/users/sign_up")
  public String showSignUp(Model model) {
    model.addAttribute("userForm", new UserForm());
    return "users/signUp";
  }

  // サインアップの画面からのリクエストを受け付け、ユーザーの登録を行う
  // ルーティングはSecurityConfig.javaの記述に合わせて/userのPOSTリクエスト
  @PostMapping("/user")
  public String createUser(@ModelAttribute("userForm") UserForm userForm, Model model) {
    // 引数で渡ってきた入力フォームの情報をエンティティに格納
    UserEntity userEntity = new UserEntity();
    userEntity.setName(userForm.getName());
    userEntity.setEmail(userForm.getEmail());
    userEntity.setPassword(userForm.getPassword());

    try {
      // INSERT文実行
      // userRepository.insert(userEntity);
      // 暗号化したパスワードを用いたINSERT文実行のメソッドを使用
      userService.createUserWithEncryptedPassword(userEntity);
    } catch (Exception e) {
      // INSERT文実行時にエラーになった場合
      System.out.println("エラー：" + e);
      model.addAttribute("userForm", userForm);
      return "users/signup";
    }

    return "redirect/:/";
  }

  // ログイン画面表示処理
  @GetMapping("/users/login")
  public String loginForm(Model model) {
    model.addAttribute("loginForm", new LoginForm());
    // ログイン画面を表示
    return "users/login";
  }

  // ログイン失敗時の処理
  @GetMapping("/login")
  public String getMethodName(@RequestParam(value = "error", required = false) String error, @ModelAttribute("loginForm") LoginForm loginForm, Model model) {
    if (error != null) {
      model.addAttribute("loginError", "メールアドレスかパスワードが間違っています。");
    }
    return "users/login";
  }
  
  // ユーザー編集画面表示処理
  @GetMapping("/users/{userId}/edit")
  public String editUserForm(@PathVariable("userId") Integer userId, Model model) {
    UserEntity user = userRepository.findById(userId);

    UserEditForm userForm = new UserEditForm();
    userForm.setId(user.getId());
    userForm.setName(user.getName());
    userForm.setEmail(user.getEmail());

    model.addAttribute("user", userForm);
    return "users/edit";
  }

  // ユーザー情報更新処理（ユーザー情報編集画面）
  @PostMapping("/users/{userId}")
  public String updateUser(@PathVariable("userId") Integer userId, @ModelAttribute("user") UserEditForm userEditForm, Model model) {
    // 画面から送られてきた情報をセット
    UserEntity user = userRepository.findById(userId);
    user.setName(userEditForm.getName());
    user.setEmail(userEditForm.getEmail());

    try {
      userRepository.update(user);
    } catch (Exception e){
      System.out.println("エラー：" + e);
      model.addAttribute("user", userEditForm);
      return "users/edit";
    }
    return "redirect:/";
  }
}

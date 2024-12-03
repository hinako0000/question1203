package com.example.springtutorial.controller;

import java.util.List;

import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springtutorial.entity.User;
import com.example.springtutorial.form.UserRegisterForm;
import com.example.springtutorial.service.UserService;

@Controller
public class AdminUserController {
	private final UserService userService;
	
	//ユーザーリスト表示
	
	//コンストラクタインジェクション(ControllerとServiceを紐づける)
	public AdminUserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/adminuser")
	public String adminUser(Model model) {
		
		// 最新のユーザーリストを取得
		List<User> users = userService.getAllUsers();
		System.out.println("users:" + users);
		
		// ビューにユーザーリストを渡す
		model.addAttribute("users",users);
		
		
		//インスタンス生成
		if(!model.containsAttribute("userRegisterForm")){
			model.addAttribute("userRegisterForm",new UserRegisterForm());
		}
		
		//ビューの呼び出し
		return "adminUserView";
		
	}
	
	//ユーザー登録処理
	@PostMapping("/register")
	public String registerUser(RedirectAttributes redirectAttributes,
			@Validated UserRegisterForm form, BindingResult result) {
		
		//バリデーションエラーがあったら終了
		if(result.hasErrors()) {
			//フォームクラスをビューに受け渡す
			redirectAttributes.addFlashAttribute("userRegisterForm",form);
			//バリデーション結果をビューに返す
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
					 + Conventions.getVariableName(form),result);
			
			// adminuserにリダイレクトしてリストを再表示
            return "redirect:/adminuser";
		}
		
		try {
		// リクエストパラメータからのデータを用いてユーザー登録
		userService.createUser(form.getUserName(), form.getPassword(), form.getRoleId());
		// 登録成功時は完了メッセージをビューに受け渡す
		redirectAttributes.addFlashAttribute("successMessage","ユーザー登録が完了しました");
		
		}catch(IllegalArgumentException e) {
			// 登録失敗時はエラーメッセージをビューに受け渡す
			redirectAttributes.addFlashAttribute("failureMessage",e.getMessage());
			System.out.println("エラーメッセージ" + e.getMessage());
			
			redirectAttributes.addFlashAttribute("userRegisterForm",form);
		}
		
		// adminuserにリダイレクトしてリストを再表示
		return "redirect:/adminuser";
		
		
	}
	
	
	
	

}

package com.example.springtutorial.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyController {
	//springtutorial.nameプロパティの値を取得
	@Value("${springtutorial.name:guest}")
	private String propertyName;
	
	//springtutorial.nameプロパティの値を画面に表示
	@GetMapping("/property")
	public String getProperty() {
		return propertyName;
	}
	

}

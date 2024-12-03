package com.example.springtutorial.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class OneSecEventListener {
	//秒数count
	private int count;
	
	//イベントの発行を受けてcountを進める
	@EventListener
	private void onOneSecEvent(OneSecEvent event) {
		this.count++;
	}

}

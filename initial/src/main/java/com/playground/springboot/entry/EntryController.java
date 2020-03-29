package com.playground.springboot.entry;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@EnableAutoConfiguration
public class EntryController {

//	private final String lightProcessorHost;
//
//	private final int lightProcessorPort;
//
//	private final RestTemplate restTemplate;

	private String byeURL;

//	public EntryController(String lightProcessorHost, int lightProcessorPort, RestTemplate restTemplate) {
//		this.lightProcessorHost = lightProcessorHost;
//		this.lightProcessorPort = lightProcessorPort;
//		this.restTemplate = restTemplate;
//		byeURL = lightProcessorHost +":"+ lightProcessorPort;
//	}

	@RequestMapping("/")
	public String index() {
		String helloMsg = "Hello, I love you, won't you tell me your name?\n";
//		ProcessingBean bye = restTemplate.getForObject(byeURL, ProcessingBean.class);
//		return helloMsg + "\n" + bye;
		return "";
	}

}
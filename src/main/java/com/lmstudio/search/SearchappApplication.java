package com.lmstudio.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.lmstudio.search.batch.SearchSettings;
/**
 * SpringBoot默认会扫描启动类同包以及子包下的注解
* TODO
* @ClassName: SearchappApplication
* @author jason
 */
@SpringBootApplication
@EnableConfigurationProperties({SearchSettings.class})
@EnableScheduling
public class SearchappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchappApplication.class, args);
	}
}

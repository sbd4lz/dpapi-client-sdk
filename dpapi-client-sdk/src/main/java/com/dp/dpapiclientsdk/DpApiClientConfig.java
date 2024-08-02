package com.dp.dpapiclientsdk;

import com.dp.dpapiclientsdk.client.DpApiClient;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("dp.api")
@Data
@ComponentScan
public class DpApiClientConfig {

	private String accessKey;
	private String secretKey;

	@Bean
	@ConditionalOnMissingBean
	public DpApiClient dpApiClient(){
		return new DpApiClient(accessKey, secretKey);
	}

}

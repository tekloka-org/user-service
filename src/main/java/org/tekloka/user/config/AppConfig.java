package org.tekloka.user.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.tekloka.user.util.AuditorAwareUtil;

@Configuration
public class AppConfig {
    
	@Value("${jasypt.encryptor.password}") 
	private String jasyptEncryptionKey;
	
	@Bean("jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor() {
		var encryptor = new PooledPBEStringEncryptor();
        var config = new SimpleStringPBEConfig();
        config.setPassword(jasyptEncryptionKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
	
	@Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareUtil();
    }
}
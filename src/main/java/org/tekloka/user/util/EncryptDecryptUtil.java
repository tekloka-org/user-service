package org.tekloka.user.util;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EncryptDecryptUtil {

	@Autowired
	StringEncryptor jasyptStringEncryptor;
	
	public String encrypt(String unencryptedString) {
		return jasyptStringEncryptor.encrypt(unencryptedString);
	}
	
	public String decrypt(String encryptedString) {
		return jasyptStringEncryptor.decrypt(encryptedString);
	}
	
}

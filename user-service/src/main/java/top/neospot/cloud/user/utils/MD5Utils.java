package top.neospot.cloud.user.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5Utils {
	private static final String SALT = "xbNIxrQfn6COSYn1/GdloA==";

	private static final String ALGORITH_NAME = "md5";

	private static final int HASH_ITERATIONS = 2;

	public static String encrypt(String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
		return newPassword;
	}

	public static String encrypt(String username, String password) {
		return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(username + SALT),
				HASH_ITERATIONS).toHex();
	}

	public static String encrypt(String username, String password, String salt) {
		return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(username + salt),
				HASH_ITERATIONS).toHex();
	}
	public static void main(String[] args) {
		
		System.out.println(MD5Utils.encrypt("admin", "123456"));
	}

}
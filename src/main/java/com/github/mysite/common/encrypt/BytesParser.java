package com.github.mysite.common.encrypt;

import javax.xml.bind.DatatypeConverter;


public class BytesParser {

	public static String toHexString(byte[] array) {
	    return DatatypeConverter.printHexBinary(array);
	}

	public static byte[] toByteArray(String s) {
	    return DatatypeConverter.parseHexBinary(s);
	}
}

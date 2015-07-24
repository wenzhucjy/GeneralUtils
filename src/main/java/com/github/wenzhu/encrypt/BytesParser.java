package com.github.wenzhu.encrypt;

import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class BytesParser {
	private int currPos;
	private byte[] data;

	// Ref: BytesWrapper
	public BytesParser(byte[] data, int currPos) {
		this.data = data;
		this.currPos = currPos;
	}

	public BytesParser(byte[] data) {
		this(data, 0);
	}

	public void setCurrPos(int currPos) {
		this.currPos = currPos;
	}

	public int getCurrPos() {
		return currPos;
	}

	public void skipBytes(int skippedNum) {
		currPos += skippedNum;
	}

	// ============ member methods ============
	public byte getByte() {
		return parseAsByte(data, currPos++);
	}

	public short getInt16() {
		short ret = parseAsInt16(data, currPos);
		currPos += 2;
		return ret;
	}

	public int getInt() {
		int ret = parseAsInt(data, currPos);
		currPos += 4;
		return ret;
	}

	public long getLong() {
		long ret = parseAsLong(data, currPos);
		currPos += 8;
		return ret;
	}

	// ============ static methods ============

	public static int parseAsInt(byte[] data) {
		return parseAsInt(data, 0);
	}
	public static int parseAsInt(byte[] data, int offset) {
		return (data[offset] & 0xFF)
			| (data[offset+1] & 0xFF) << 8
			| (data[offset+2] & 0xFF) << 16
			| (data[offset+3] & 0xFF) << 24;
	}

	public static short parseAsInt16(byte[] data) {
		return parseAsInt16(data, 0);
	}
	public static short parseAsInt16(byte[] data, int offset) {
		return (short) ((data[offset] & 0xFF)
			| (data[offset+1] & 0xFF) << 8);
	}

	public static byte parseAsByte(byte[] data) {
		return parseAsByte(data, 0);
	}
	public static byte parseAsByte(byte[] data, int offset) {
		return (byte) data[offset];
	}

	public static long parseAsLong(byte[] data) {
		return parseAsLong(data, 0);
	}
	public static long parseAsLong(byte[] data, int offset) {
		return (long)(data[offset] & 0xFF)
				| (long)(data[offset+1] & 0xFF) << 8
				| (long)(data[offset+2] & 0xFF) << 16
				| (long)(data[offset+3] & 0xFF) << 24
				| (long)(data[offset+4] & 0xFF) << 32
				| (long)(data[offset+5] & 0xFF) << 40
				| (long)(data[offset+6] & 0xFF) << 48
				| (long)(data[offset+7] & 0xFF) << 56;
	}
	
	public static byte[] to4LEBytes(int value) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		return bb.putInt(value).array();
	}
	
	public static byte[] set4LEBytes(int data, byte[] storage) {
		return set4LEBytes((long) data, storage);
	}
	
	public static byte[] setBytes(int data, byte[] storage) {
		return set2LEBytes(data, storage, 0);
	}
	
	public static byte[] setBytes(int data, byte[] storage, int offset) {
		if (storage.length >= (offset+1)) {
			storage[offset+0] = (byte)(data&0x000000ff);
		}
		return storage;
	}

	public static byte[] set2LEBytes(int data, byte[] storage) {
		return set2LEBytes(data, storage, 0);
	}
	
	public static byte[] set2LEBytes(int data, byte[] storage, int offset) {
		if (storage.length >= (offset+2)) {
			storage[offset+0] = (byte)(data&0x000000ff);
			storage[offset+1] = (byte)((data&0x0000ff00)>>>8);
		}
		return storage;
	}
	
	public static byte[] set4LEBytes(long data, byte[] storage) {
		return set4LEBytes(data, storage, 0);
	}
	
	public static byte[] set4LEBytes(long data, byte[] storage, int offset) {
		if (storage.length >= (offset+4)) {
			storage[offset+0] = (byte)(data&0x000000ff);
			storage[offset+1] = (byte)((data&0x0000ff00)>>>8);
			storage[offset+2] = (byte)((data&0x00ff0000)>>>16);
			storage[offset+3] = (byte)((data&0xff000000)>>>24);
		}
		return storage;
	}
	
	public static byte[] set8LEBytes(long data, byte[] storage) {
		if (storage.length >= 8) {
			storage[0] = (byte)(data&0x000000ff);
			storage[1] = (byte)((data&0x0000ff00)>>>8);
			storage[2] = (byte)((data&0x00ff0000)>>>16);
			storage[3] = (byte)((data&0xff000000)>>>24);
			storage[4] = (byte)((data&0xff000000)>>>32);
			storage[5] = (byte)((data&0xff000000)>>>40);
			storage[6] = (byte)((data&0xff000000)>>>48);
			storage[7] = (byte)((data&0xff000000)>>>56);
			
		}
		return storage;
	}
	
	public static String toHexString(byte[] array) {
	    return DatatypeConverter.printHexBinary(array);
	}

	public static byte[] toByteArray(String s) {
	    return DatatypeConverter.parseHexBinary(s);
	}
}

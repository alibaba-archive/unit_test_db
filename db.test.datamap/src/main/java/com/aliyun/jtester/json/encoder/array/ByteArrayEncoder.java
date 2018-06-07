package com.aliyun.jtester.json.encoder.array;

import com.aliyun.jtester.json.encoder.JSONEncoder;
import com.aliyun.jtester.json.encoder.single.fixed.ByteEncoder;

@SuppressWarnings("rawtypes")
public class ByteArrayEncoder extends ArraysEncoder<byte[]> {
	public final static ByteArrayEncoder instance = new ByteArrayEncoder();

	private ByteArrayEncoder() {
		super(byte.class);
	}

	@Override
	protected int getArraySize(byte[] target) {
		return target.length;
	}

	@Override
	protected JSONEncoder getEncoderByItem(Object item) {
		return ByteEncoder.instance;
	}

	@Override
	protected Object getItemByIndex(byte[] target, int index) {
		return target[index];
	}
}

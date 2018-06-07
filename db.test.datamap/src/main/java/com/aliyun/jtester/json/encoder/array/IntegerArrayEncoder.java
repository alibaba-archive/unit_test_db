package com.aliyun.jtester.json.encoder.array;

import com.aliyun.jtester.json.encoder.single.fixed.IntegerEncoder;
import com.aliyun.jtester.json.encoder.JSONEncoder;
import com.aliyun.jtester.json.encoder.single.fixed.IntegerEncoder;

@SuppressWarnings({ "rawtypes" })
public class IntegerArrayEncoder extends ArraysEncoder<int[]> {
	public static final IntegerArrayEncoder instance = new IntegerArrayEncoder();

	private IntegerArrayEncoder() {
		super(int[].class);
	}

	@Override
	protected int getArraySize(int[] target) {
		return target.length;
	}

	@Override
	protected JSONEncoder getEncoderByItem(Object item) {
		return IntegerEncoder.instance;
	}

	@Override
	protected Object getItemByIndex(int[] target, int index) {
		return target[index];
	}
}

package com.aliyun.jtester.json.encoder.single.fixed;

import java.io.Writer;

import com.aliyun.jtester.json.encoder.single.FixedTypeEncoder;
import com.aliyun.jtester.json.encoder.single.FixedTypeEncoder;

public class ByteEncoder extends FixedTypeEncoder<Byte> {
	public static ByteEncoder instance = new ByteEncoder();

	private ByteEncoder() {
		super(Byte.class);
	}

	@Override
	protected void encodeSingleValue(Byte target, Writer writer) throws Exception {
		writer.append(String.valueOf(target));
	}
}

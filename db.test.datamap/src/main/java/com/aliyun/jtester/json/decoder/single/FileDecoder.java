package com.aliyun.jtester.json.decoder.single;

import java.io.File;
import java.lang.reflect.Type;

import com.aliyun.jtester.json.decoder.base.FixedTypeDecoder;
import com.aliyun.jtester.json.decoder.base.FixedTypeDecoder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FileDecoder extends FixedTypeDecoder {
	public final static FileDecoder toFILE = new FileDecoder();

	public boolean accept(Type type) {
		Class claz = this.getRawType(type, null);
		return File.class.isAssignableFrom(claz);
	}

	@Override
	protected File decodeFromString(String value) {
		return new File(value);
	}
}

package com.aliyun.jtester.hamcrest.iassert.object.impl;

import java.io.File;

import com.aliyun.ext.jtester.hamcrest.Matcher;
import com.aliyun.jtester.hamcrest.iassert.common.impl.BaseAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IFileAssert;
import com.aliyun.jtester.hamcrest.matcher.file.FileExistsMatcher;
import com.aliyun.jtester.hamcrest.matcher.file.FileMatchers;
import com.aliyun.jtester.hamcrest.iassert.common.impl.BaseAssert;
import com.aliyun.jtester.hamcrest.iassert.object.intf.IFileAssert;
import com.aliyun.jtester.hamcrest.matcher.file.FileExistsMatcher;
import com.aliyun.jtester.hamcrest.matcher.file.FileMatchers;
import com.aliyun.jtester.hamcrest.matcher.file.FileExistsMatcher.FileExistsMatcherType;

public class FileAssert extends BaseAssert<File, IFileAssert> implements IFileAssert {
	public FileAssert() {
		super(IFileAssert.class);
		this.valueClaz = File.class;
	}

	public FileAssert(File file) {
		super(file, IFileAssert.class);
		this.valueClaz = File.class;
	}

	public IFileAssert isExists() {
		FileExistsMatcher matcher = new FileExistsMatcher((File) this.value, FileExistsMatcher.FileExistsMatcherType.ISEXISTS);
		return this.assertThat(matcher);
	}

	public IFileAssert unExists() {
		FileExistsMatcher matcher = new FileExistsMatcher((File) this.value, FileExistsMatcher.FileExistsMatcherType.UNEXISTS);
		return this.assertThat(matcher);
	}

	public IFileAssert nameContain(String expected) {
		Matcher<?> matcher = FileMatchers.nameContain(expected);
		return this.assertThat(matcher);
	}

	public IFileAssert nameEq(String expected) {
		Matcher<?> matcher = FileMatchers.nameEq(expected);
		return this.assertThat(matcher);
	}
}

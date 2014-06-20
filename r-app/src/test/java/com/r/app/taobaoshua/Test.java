package com.r.app.taobaoshua;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test {

	@org.junit.Test
	public void test() throws UnsupportedEncodingException {
		String post = "Username=oky120002&Password=lian1990&Question=%CE%D2%D6%D0%D1%A7%D0%A3%C3%FB%C8%AB%B3%C6%CA%C7%CA%B2%C3%B4%A3%BF&Answer=%D1%EE%BC%D2%C6%BA%D6%D0%D1%A7&Verifycode=1358&u1=";
		String encode = URLDecoder.decode(post, "utf-8");
		System.out.println(encode);
		
		
		String post2 = "Username=oky120002&Password=lian1990&Question=我中学校名全称是什么？&Answer=杨家坪中学&Verifycode=3438&u1=";
		String encode2 = URLEncoder.encode(post2, "gb2312");
		System.out.println(encode2);
		
		
		// 源代码
		// Username=oky120002&Password=lian1990&Question=%CE%D2%D6%D0%D1%A7%D0%A3%C3%FB%C8%AB%B3%C6%CA%C7%CA%B2%C3%B4%A3%BF&Answer=%D1%EE%BC%D2%C6%BA%D6%D0%D1%A7&Verifycode=3438&u1=
		// Username=oky120002&Password=lian1990&Question=%CE%D2%D6%D0%D1%A7%D0%A3%C3%FB%C8%AB%B3%C6%CA%C7%CA%B2%C3%B4%A3%BF&Answer=%D1%EE%BC%D2%C6%BA%D6%D0%D1%A7&Verifycode=1358&u1=
		// Username=oky120002&Password=lian1990&Question=我中学校名全称是什么？&Answer=杨家坪中学&Verifycode=3438&u1=
		
	}

}

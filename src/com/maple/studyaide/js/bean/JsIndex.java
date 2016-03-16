package com.maple.studyaide.js.bean;

import java.util.List;

/**
 * 题库数据
 * 
 * @author shaoshuai
 * 
 */
public class JsIndex {
	public int retcode;// 返回代码
	public List<JsContent> content;// 商品数据

	/** 题库数据 */
	public class JsContent {
		public JsDaan daan;//
		public JsDetail detail;//
		public JsTimu timu;//
		public JsTypes types;//
	}

	/** 正确答案 */
	public class JsDaan {
		public String daan_one;//
		public String daan_tow;//
		public String daan_three;//
		public String daan_four;//
	}

	/** 解说 */
	public class JsDetail {
		public String detail;//
	}

	/** 题库 */
	public class JsTimu {
		public String title;// 题目说明

		public String one;// 第一个选项内容
		public String tow;//
		public String three;//
		public String four;//
	}

	/** 类型 */
	public class JsTypes {
		public String types;// 类型
	}
}

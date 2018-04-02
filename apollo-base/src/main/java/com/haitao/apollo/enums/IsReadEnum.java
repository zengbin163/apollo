/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月11日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.enums;

/**
 * @ClassName: IsReadEnum
 * @Description: 消息盒子的消息是否阅读
 * @author zengbin
 * @date 2015年11月22日 下午9:02:45
 */
public enum IsReadEnum {
	UN_READED(0, "未读"), 
	IS_READER(1, "已读");

	private IsReadEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	private Integer code;

	private String name;

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static IsReadEnum getEnum(Integer code) {
		for (IsReadEnum isReadEnum : IsReadEnum.values()) {
			if (isReadEnum.getCode().equals(code)) {
				return isReadEnum;
			}
		}
		return null;
	}

}

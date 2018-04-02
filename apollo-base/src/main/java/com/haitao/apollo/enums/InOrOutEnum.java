/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年11月11日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.haitao.apollo.enums;

/**
 * @ClassName: InOrOutEnum
 * @Description: 入账还是出账
 * @author zengbin
 * @date 2016年03月27日 下午9:02:45
 */
public enum InOrOutEnum {
	IN_ACCOUNT(1, "入账"), 
	OUT_ACCOUNT(-1, "出账");

	private InOrOutEnum(Integer code, String name) {
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

	public static InOrOutEnum getEnum(Integer code) {
		for (InOrOutEnum inOrOutEnum : InOrOutEnum.values()) {
			if (inOrOutEnum.getCode().equals(code)) {
				return inOrOutEnum;
			}
		}
		return null;
	}

}

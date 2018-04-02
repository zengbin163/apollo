package com.haitao.apollo.status;

import java.io.Serializable;

/**
 * 客服状态
 * @author zengbin
 *
 */
public class CsStatus implements Serializable{
	private static final long serialVersionUID = 6590953336656382828L;
	public static final Integer IN_CUSTOMER=                401;// 客服已介入
	public static final Integer FINISH_CUSTOMER=            402;// 客服已完成
	public static final Integer CLOSE_CUSTOMER=             403;// 客服已取消
}

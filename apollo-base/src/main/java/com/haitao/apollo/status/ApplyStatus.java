package com.haitao.apollo.status;

import java.io.Serializable;

/**
 * 买手提现申请状态
 * 
 * @author zengbin
 *
 */
public class ApplyStatus implements Serializable {
	private static final long serialVersionUID = -3355953255390589795L;
	public static final Integer IN_APPLY = 0;// 申请中
	public static final Integer APPLY_FINISH = 1;// 完结
}

package com.haitao.apollo.global;

/**
 * 全局常量
 * @author zengbin
 *
 */
public class GlobalConstants {
	public static final double DEPOSIT_PERCENT = 0.4;//定金4成
	public static final double FINAL_PERCENT = 0.6;//尾款6成
	public static final double DEDU_PERCENT = 0.1;//买手取消订单处罚1%的保证金
	public static final double DEDU_UPPER = 500;//买手取消订单处罚1%的保证金，上限500

	public static final double FROZEN_PERCENT_1 = 0.1;//在超过采购时长第一天，冻结订单金额1%的保证金
	public static final double FROZEN_PERCENT_2 = 0.2;//第二天，冻结订单金额的2%的保证金
	public static final double FROZEN_PERCENT_3 = 0.3;//第三天，冻结订单金额的3%的保证金
	public static final double FROZEN_PERCENT_6 = 0.6;//超过72小时扣除订单金额的6%的保证金给用户，同时退款给用户
	
	public static final double DIVIDE_PERCENT = 0.01;//元转化为分
}

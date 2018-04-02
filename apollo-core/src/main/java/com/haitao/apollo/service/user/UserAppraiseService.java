package com.haitao.apollo.service.user;

public interface UserAppraiseService {
	
	public static final Integer SCORE_1 = 1;
	public static final Integer SCORE_2 = 2;
	public static final Integer SCORE_3 = 3;
	public static final Integer SCORE_4 = 4;
	public static final Integer SCORE_5 = 5;
	
	/**
	 * 消费者发表评价
	 * @param userId 用户id
	 * @param purchaserId 买手id
	 * @param isAnonym 是否匿名
	 * @param orderId 订单id
	 * @param score  评分 1-5分之间
	 * @param content  评价内容
	 * @return
	 */
	Integer appraise(Integer userId, Integer purchaserId, Integer isAnonym, Integer orderId, Integer score, String content);
}

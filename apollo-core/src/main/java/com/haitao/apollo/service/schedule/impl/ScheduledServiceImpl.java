package com.haitao.apollo.service.schedule.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.haitao.apollo.service.schedule.ScheduledService;
import com.haitao.apollo.service.schedule.impl.acept.AbstractAcceptTimeoutStrategy;
import com.haitao.apollo.service.schedule.impl.agree.AbstractAgreeShipmentTimeoutStrategy;
import com.haitao.apollo.service.schedule.impl.appraise.AbstractAppraiseTimeoutStrategy;
import com.haitao.apollo.service.schedule.impl.confirm.AbstractConfirmTimeoutStrategy;
import com.haitao.apollo.service.schedule.impl.shipment.AbstractShipmentTimeoutStrategy;
import com.haitao.apollo.util.LoggerUtil;

/**
 * 定时任务服务
 * @author zengbin
 *
 */
public class ScheduledServiceImpl implements ScheduledService {
    private Integer isExecuteMachine;
    private Long acceptPurchaserTimeout;
    private Long acceptPublicTimeout;
    private Long agreeShipmentTimeout;
    private Long confirmTimeout;
    private Long appraiseTimeout;

    @Resource(name = "acceptTimeoutStrategyMap")
    private Map<String,AbstractAcceptTimeoutStrategy> acceptTimeoutStrategyMap;
    @Resource(name = "agreeShipmentTimeoutStrategyMap")
    private Map<String,AbstractAgreeShipmentTimeoutStrategy> agreeShipmentTimeoutStrategyMap;
    @Resource(name = "purchaserShipmentTimeoutStrategyMap")
    private Map<String,AbstractShipmentTimeoutStrategy> purchaserShipmentTimeoutStrategyMap;
    @Resource(name = "userConfirmTimeoutStrategyMap")
    private Map<String,AbstractConfirmTimeoutStrategy> userConfirmTimeoutStrategyMap;
    @Resource(name = "userAppraiseTimeoutStrategyMap")
    private Map<String,AbstractAppraiseTimeoutStrategy> userAppraiseTimeoutStrategyMap;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledServiceImpl.class);
    
	@Override
	@Scheduled(cron="0 0 0/1 * * ?")
	public void acceptTimeoutTask() {
		if(!isExecute()){
			return;
		}
		LoggerUtil.INFO(logger, "=============买手接单超时JOB启动============");
        //校验各种策略
        for(Map.Entry<String, AbstractAcceptTimeoutStrategy> acceptTimeoutMap : acceptTimeoutStrategyMap.entrySet()){
        	AbstractAcceptTimeoutStrategy acceptTimeoutStrategy = acceptTimeoutMap.getValue();
        	acceptTimeoutStrategy.execute(acceptPurchaserTimeout, acceptPublicTimeout);
        }
		LoggerUtil.INFO(logger, "=============买手接单超时JOB结束============");
	}

	@Override
	@Scheduled(cron="0 0 0/1 * * ?")
	public void agreeShipmentTimeoutTask() {
		if(!isExecute()){
			return;
		}
		LoggerUtil.INFO(logger, "=============消费者同意采购时间超时JOB启动============");
        //校验各种策略
        for(Map.Entry<String, AbstractAgreeShipmentTimeoutStrategy> agreeShipmentTimeoutMap : agreeShipmentTimeoutStrategyMap.entrySet()){
        	AbstractAgreeShipmentTimeoutStrategy agreeShipmentTimeoutStrategy = agreeShipmentTimeoutMap.getValue();
        	agreeShipmentTimeoutStrategy.execute(agreeShipmentTimeout);
        }
        LoggerUtil.INFO(logger, "=============消费者同意采购时间超时JOB结束============");
	}

	@Override
	@Scheduled(cron="0 0 0/1 * * ?")
	public void shipmentTimeoutTask() {
		if(!isExecute()){
			return;
		}
		LoggerUtil.INFO(logger, "=============买手发货超时JOB启动============");
		//校验各种策略
        for(Map.Entry<String, AbstractShipmentTimeoutStrategy> shipmentTimeoutMap : purchaserShipmentTimeoutStrategyMap.entrySet()){
        	AbstractShipmentTimeoutStrategy shipmentTimeoutStrategy = shipmentTimeoutMap.getValue();
        	shipmentTimeoutStrategy.execute();
        }		
        LoggerUtil.INFO(logger, "=============买手发货超时JOB结束============");
	}

	@Override
	@Scheduled(cron="0 0 0/1 * * ?")
	public void confirmTimeoutTask() {
		if(!isExecute()){
			return;
		}
		LoggerUtil.INFO(logger, "=============用户确认收货超时JOB启动============");
        //校验各种策略
        for(Map.Entry<String, AbstractConfirmTimeoutStrategy> userConfirmTimeoutMap : userConfirmTimeoutStrategyMap.entrySet()){
        	AbstractConfirmTimeoutStrategy userConfirmTimeoutStrategy = userConfirmTimeoutMap.getValue();
        	userConfirmTimeoutStrategy.execute(confirmTimeout);
        }
		LoggerUtil.INFO(logger, "=============用户确认收货超时JOB结束============");
	}
	
	@Override
	@Scheduled(cron="0 0 0/1 * * ?")
	public void appraiseTimeoutTask() {
		if(!isExecute()){
			return;
		}
		LoggerUtil.INFO(logger, "=============用户评论超时JOB启动============");
        //校验各种策略
        for(Map.Entry<String, AbstractAppraiseTimeoutStrategy> userAppraiseTimeoutMap : userAppraiseTimeoutStrategyMap.entrySet()){
        	AbstractAppraiseTimeoutStrategy userAppraiseTimeoutStrategy = userAppraiseTimeoutMap.getValue();
        	userAppraiseTimeoutStrategy.execute(appraiseTimeout);
        }
		LoggerUtil.INFO(logger, "=============用户评论超时JOB结束============");
	}

	private boolean isExecute(){
		return isExecuteMachine==1 ? true : false;
	}
	
	public Integer getIsExecuteMachine() {
		return isExecuteMachine;
	}

	public void setIsExecuteMachine(Integer isExecuteMachine) {
		this.isExecuteMachine = isExecuteMachine;
	}

	public Long getConfirmTimeout() {
		return confirmTimeout;
	}

	public void setConfirmTimeout(Long confirmTimeout) {
		this.confirmTimeout = confirmTimeout;
	}

	public Long getAcceptPurchaserTimeout() {
		return acceptPurchaserTimeout;
	}

	public void setAcceptPurchaserTimeout(Long acceptPurchaserTimeout) {
		this.acceptPurchaserTimeout = acceptPurchaserTimeout;
	}

	public Long getAcceptPublicTimeout() {
		return acceptPublicTimeout;
	}

	public void setAcceptPublicTimeout(Long acceptPublicTimeout) {
		this.acceptPublicTimeout = acceptPublicTimeout;
	}

	public Long getAgreeShipmentTimeout() {
		return agreeShipmentTimeout;
	}

	public void setAgreeShipmentTimeout(Long agreeShipmentTimeout) {
		this.agreeShipmentTimeout = agreeShipmentTimeout;
	}

	public Long getAppraiseTimeout() {
		return appraiseTimeout;
	}

	public void setAppraiseTimeout(Long appraiseTimeout) {
		this.appraiseTimeout = appraiseTimeout;
	}
}

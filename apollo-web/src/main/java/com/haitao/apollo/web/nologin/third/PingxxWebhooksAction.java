package com.haitao.apollo.web.nologin.third;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.FundTypeEnum;
import com.haitao.apollo.enums.PayTypeEnum;
import com.haitao.apollo.pay.Component;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.exception.ApolloSysException;
import com.haitao.apollo.proccess.ThirdPayProcess;
import com.haitao.apollo.util.LoggerUtil;
import com.haitao.apollo.util.WebHooksVerifyUtil;
import com.haitao.apollo.web.BaseAction;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.EventData;
import com.pingplusplus.model.Webhooks;


/**
 * Pingxx支付或者退款回写URL
 * 若返回状态码不是 2xx，Ping++ 服务器会在 25 小时内向你的服务器进行多次发送，最多 8 次。
 * Webhooks 首次是即时推送，8次时间间隔为 2min、10min、10min、1h、2h、6h、15h，直到你正确回复状态 2xx或者超过最大重发次数，我们将不再发送。
 * @author zengbin
 *
 */
public class PingxxWebhooksAction extends BaseAction {
	private static final long serialVersionUID = -7181967531658034246L;
	@Autowired
	private ThirdPayProcess thirdPayProcess;
	private static final Logger logger = LoggerFactory.getLogger(PingxxWebhooksAction.class);
	
	@SuppressWarnings("unchecked")
	public String callback(){
		/**
		 * 校验签名 
		 */
		String signature = request.getHeader("x-pingplusplus-signature");
		StringBuffer buffer = new StringBuffer();
		try {
			request.setCharacterEncoding("UTF8");
			// 获得 http body 内容
			BufferedReader reader = request.getReader();
			String string = null;
			while ((string = reader.readLine()) != null) {
				buffer.append(string);
			}
			reader.close();
		}catch(Exception ex){
			LoggerUtil.ERROR(logger, "系统异常", ex);
		}
        ServletContext servletContext = request.getServletContext();
        String pubKeyPath = servletContext.getRealPath("/") + "/WEB-INF/";

        byte[] bytes1= buffer.toString().getBytes();
        byte[] bytes2 = Base64.decodeBase64(signature);

        boolean signatureResult;
        try {
            signatureResult = WebHooksVerifyUtil.verifyData(bytes1, bytes2, WebHooksVerifyUtil.getPubKey(pubKeyPath + "pingxx.pub"));
        } catch (Exception e) {
            signatureResult = false;
        }
        if(!signatureResult) {
        	throw new ApolloSysException(ResultCode.SIGNATURE_VERIFY_ERROR, -1000, String.format("pingxx支付webhooks回写签名验证失败"));
        }
		try{
			// 解析异步通知数据
			Event event = Webhooks.eventParse(buffer.toString());
			if(null==event) {
				response.setStatus(500);
				buffer.append("请求异常，event为空!");
			}else{
				if ("charge.succeeded".equals(event.getType())) {
					EventData eventData = event.getData();
					JSONObject json = JSONObject.parseObject(eventData.toString());
					JSONObject json2x = JSONObject.parseObject(json.getString("object"));
					String alipayOrderNo = (String) json2x.get(Component.ORDER_NO);
					Integer orderNo = 0; //业务系统订单id（悬赏id或者买手id）
					if(alipayOrderNo.startsWith(FundTypeEnum.DEPOSIT.toString())) {
						orderNo = new Integer(alipayOrderNo.substring(FundTypeEnum.DEPOSIT.toString().length()));
					} else if(alipayOrderNo.startsWith(FundTypeEnum.FINAL.toString())) {
						orderNo = new Integer(alipayOrderNo.substring(FundTypeEnum.FINAL.toString().length()));
					} else if(alipayOrderNo.startsWith(FundTypeEnum.GUARANTEE.toString())) {
						orderNo = new Integer(alipayOrderNo.substring(FundTypeEnum.GUARANTEE.toString().length()));
					} else {
						throw new ApolloBizException(String.format("赏购第三方支付只支持定金、尾款、买手保证金，[alipayOrderNo=]", alipayOrderNo));
					}
					String paySerialNo = (String) json2x.get(Component.PAY_SERIAL_NO);//支付流水号
					String channel = (String) json2x.get(Component.CHANNEL);//wx alipay
					Integer payType = PayTypeEnum.getEnumByName(channel).getCode();
					BigDecimal payAmount = new BigDecimal(json2x.get(Component.AMOUNT).toString());
					String subject = (String) json2x.get(Component.SUBJECT);
					Map<String, String> metadataMap = ((Map<String, String>) json2x.get(Component.METADATA_MAP));
					Integer fundType = null;
					BigDecimal bigMoney = null;
					if (null != metadataMap) {
						fundType = new Integer(metadataMap.get(Component.FUND_TYPE));
						bigMoney = new BigDecimal(metadataMap.get(Component.BIG_MONEY));
					}
					this.thirdPayProcess.payCallBack(orderNo, paySerialNo, payType, fundType, payAmount, bigMoney, subject);
					response.setStatus(200);
				} else if ("refund.succeeded".equals(event.getType())) {
					EventData eventData = event.getData();
					JSONObject json = JSONObject.parseObject(eventData.toString());
					String paySerialNo = (String) json.get(Component.CHARGE);//支付流水号
					thirdPayProcess.refundCallBack(paySerialNo);
					response.setStatus(200);
				} else {
					response.setStatus(500);
				}
		   }
		}catch(ApolloBizException ape){
			LoggerUtil.ERROR(logger, ape.getResultCode().getString(), ape);
			buffer.append("请求异常，" + String.format("resultCode=%s，resultString=%s", ape.getResultCode(), ape.getResultCode().getString()));
			response.setStatus(500);
		}
		this.returnFastJSON(buffer.toString());
		return null;
	}
}

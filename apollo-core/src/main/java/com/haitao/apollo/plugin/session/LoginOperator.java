package com.haitao.apollo.plugin.session;

import java.io.Serializable;

import com.haitao.apollo.pojo.purchaser.Purchaser;
import com.haitao.apollo.pojo.user.User;

public class LoginOperator implements Serializable {
	private static final long serialVersionUID = 3659371384709242713L;

	private Integer id;
	private String name;
	private String mobile;
	private String password;
	private String inviteCode;
    private Integer isForbidPost;//是否禁止悬赏 0:未禁止   1:已禁止
    private Integer isForbidLogin;//是否禁止登陆  0:未禁止  1:已禁止
    private Integer isForbidShow;//是否禁止晒单  0:未禁止   1:已禁止
	
	public static LoginOperator transUser2Operator(User user){
		LoginOperator loginOperator = new LoginOperator();
		loginOperator.setId(user.getId());
		loginOperator.setName(user.getNickName());
		loginOperator.setMobile(user.getMobile());
		loginOperator.setPassword(user.getPassword());
		loginOperator.setInviteCode(user.getInviteCode());
		loginOperator.setIsForbidLogin(user.getIsForbidLogin());
		loginOperator.setIsForbidPost(user.getIsForbidPost());
		loginOperator.setIsForbidShow(user.getIsForbidShow());
		return loginOperator;
	}

	public static LoginOperator transPurchaser2Operator(Purchaser purchaser){
		LoginOperator loginOperator = new LoginOperator();
		loginOperator.setId(purchaser.getId());
		loginOperator.setName(purchaser.getPurchaserName());
		loginOperator.setMobile(purchaser.getMobile());
		loginOperator.setPassword(purchaser.getPassword());
		return loginOperator;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public Integer getIsForbidPost() {
		return isForbidPost;
	}

	public void setIsForbidPost(Integer isForbidPost) {
		this.isForbidPost = isForbidPost;
	}

	public Integer getIsForbidLogin() {
		return isForbidLogin;
	}

	public void setIsForbidLogin(Integer isForbidLogin) {
		this.isForbidLogin = isForbidLogin;
	}

	public Integer getIsForbidShow() {
		return isForbidShow;
	}

	public void setIsForbidShow(Integer isForbidShow) {
		this.isForbidShow = isForbidShow;
	}
}

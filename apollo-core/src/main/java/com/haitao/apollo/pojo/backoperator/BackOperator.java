package com.haitao.apollo.pojo.backoperator;

import java.io.Serializable;

/**
 * 后台用户对象
 * 
 * @author zengbin
 *
 */
public class BackOperator implements Serializable {

	private static final long serialVersionUID = -6893108426226981472L;

	private Integer id;
	private String mobile;
	private String email;
	private String password;
	private Long createTime;
	private Long modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}
}

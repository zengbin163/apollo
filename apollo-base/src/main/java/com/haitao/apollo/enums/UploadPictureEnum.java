package com.haitao.apollo.enums;

/**
* @ClassName: UploadPictureEnum 
* @Description: 上传图片类型
* @author zengbin
* @date 2015年11月25日
 */
public enum UploadPictureEnum {
    
    PICTURE_HEADER(0, "个人头像", "/header/"), 
    PICTURE_SHOWORDER(1, "晒单传图", "/showorder/"),
    PICTURE_POSTREWAED(2, "悬赏传图", "/postreward/"),
    PICTURE_RIGHTS(3, "售后维权", "/rights/"),
    PURCHASER_INFO(4, "买手资料", "/information/"),
    ;
    
    private UploadPictureEnum(Integer code, String desc, String path) {
        this.code = code;
        this.desc = desc;
        this.path = path;
    }
    
    private Integer code;
    
    private String desc;
    
    private String path;
    
    public Integer getCode() {
        return code;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public String getPath() {
		return path;
	}

	public static UploadPictureEnum getEnum(Integer code) {
        for (UploadPictureEnum uploadPictureEnum : UploadPictureEnum.values()) {
            if (uploadPictureEnum.getCode().equals(code)) {
                return uploadPictureEnum;
            }
        }
        return null;
    }
}

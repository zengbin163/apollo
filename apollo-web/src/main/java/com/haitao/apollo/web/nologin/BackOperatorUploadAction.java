package com.haitao.apollo.web.nologin;

import java.io.File;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.haitao.apollo.enums.UploadPictureEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.upload.UploadService;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.web.BaseAction;

@Scope("prototype")
@SuppressWarnings("serial")
@Results({ @Result(name = "index", location = "../../../index.jsp") })
public class BackOperatorUploadAction extends BaseAction {
    // 要和客户端添加字段时的关键字保持一致！！
    private String imgFileName;
    private File img;
    @Autowired
    private UploadService uploadService;
    private Logger logger = LoggerFactory.getLogger(BackOperatorUploadAction.class);
	/**
	 * <pre>
	 *   上传图片，返回图片路径
	 * </pre>
	 * 
	 * @param fileType
	 *            图片类型    只支持4:买手资料
	 * @param needCompress
	 *            是否需要压缩，0:不需要；1:需要
	 * @return 图片路径，需要替换_X为_R原图或者 _M缩略图
	 */
    public String upload(){
        // 图片分类
        Integer type = this.getIntParameter(request, "fileType", 4);
        Integer needCompress = this.getIntParameter(request, "needCompress", 1);//是否需要压缩，0:不需要；1:需要
        if(!UploadPictureEnum.PURCHASER_INFO.getCode().equals(type)) {
        	throw new ApolloBizException("后端系统上传只支持买手资料这个分类");
        }
        String imgUrl = this.uploadService.upload(needCompress, type, imgFileName, img, this.getServerPath());
        logger.error("=======upload=======" + imgFileName + "=======" + DateUtil.currentUTCTime() + "======" + imgUrl);
        returnFastJSON(toMap("imgUrl", imgUrl));
        return null;
    }
	
    public String getImgFileName() {
        return imgFileName;
    }
    
    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }
    
    public File getImg() {
        return img;
    }
    
    public void setImg(File img) {
        this.img = img;
    }
    
    public String uploadIndex() {
        return "index";
    }
}

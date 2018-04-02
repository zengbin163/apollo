package com.haitao.apollo.web.login;

import java.io.File;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.haitao.apollo.annotation.FromPurchaser;
import com.haitao.apollo.annotation.FromUser;
import com.haitao.apollo.plugin.upload.UploadService;
import com.haitao.apollo.util.DateUtil;
import com.haitao.apollo.web.BaseAction;

@Scope("prototype")
@SuppressWarnings("serial")
@Results({ @Result(name = "index", location = "../../../index.jsp") })
public class UploadAction extends BaseAction {
    // 要和客户端添加字段时的关键字保持一致！！
    private String imgFileName;
    private File img;
    @Autowired
    private UploadService uploadService;
    private Logger logger = LoggerFactory.getLogger(UploadAction.class);
	/**
	 * <pre>
	 *   上传图片，返回图片路径
	 * </pre>
	 * 
	 * @param fileType
	 *            图片类型    0:个人头像     1:晒单传图       2:悬赏传图      3:售后维权
	 * @param needCompress
	 *            是否需要压缩，0:不需要；1:需要
	 * @return 图片路径，需要替换_X为_R原图或者 _M缩略图
	 */
    @FromPurchaser
    @FromUser
    public String upload(){
        // 图片分类
        Integer type = this.getIntParameter(request, "fileType", 0);
        Integer needCompress = this.getIntParameter(request, "needCompress", 1);//是否需要压缩，0:不需要；1:需要
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

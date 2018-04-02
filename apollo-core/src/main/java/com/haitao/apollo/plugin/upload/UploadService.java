package com.haitao.apollo.plugin.upload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.haitao.apollo.base.ResultCode;
import com.haitao.apollo.enums.OperatorRoleEnum;
import com.haitao.apollo.enums.UploadPictureEnum;
import com.haitao.apollo.plugin.exception.ApolloBizException;
import com.haitao.apollo.plugin.exception.ApolloSysException;
import com.haitao.apollo.plugin.session.Session;
import com.haitao.apollo.plugin.session.SessionContainer;
import com.haitao.apollo.util.image.FileCopyUtil;
import com.haitao.apollo.util.image.ImageCompressUtil;

public class UploadService {

	private String fileSavePath;
	private static final String PURCHASER_PATH = "/pictures/purchaser";
	private static final String USER_PATH = "/pictures/user";
	private static final String OTHERS_PATH = "/pictures/others";
	private static final Integer NEED_COMPRESS_YES = 1;
	
	/**
	 * 图片压缩上传
	 * @param fileType  
	 * 		图片类型    0:个人头像     1:晒单传图       2:悬赏传图      3:售后维权         4:买手资料
	 * @param imgFileName   文件名称，如果xxx.jpg
	 * @param img    文件内容  
	 * @param url    请求的URL，如：http://www.baidu.com/xx/xx
	 * @return
	 */
	public String upload(Integer needCompress, Integer fileType, String imgFileName, File img, String url) {
		Session session = SessionContainer.getSession();
		Integer operatorId = null;
		Integer role = null;
		if (null != session) {
			operatorId = session.getOperatorId();
			role = session.getRole();
		} else {
			role = -10086;
		}
		UploadPictureEnum uploadPictureEnum = UploadPictureEnum.getEnum(fileType);
        // 上传的图片类别不在图片分类列表中
		if (null == uploadPictureEnum) {
			throw new ApolloBizException(ResultCode.ILLEGAL_ARGUMENT, operatorId, String.format("图片类型错误，类型值为%s", fileType));
		}
        String path = null;
        if(OperatorRoleEnum.ROLE_PURCHASER.getCode().equals(role)) {
        	path = fileSavePath  + PURCHASER_PATH + uploadPictureEnum.getPath() + operatorId;
        } else if(OperatorRoleEnum.ROLE_USER.getCode().equals(role)){
        	path = fileSavePath  + USER_PATH + uploadPictureEnum.getPath() + operatorId;
        } else {
        	path = fileSavePath  + OTHERS_PATH + uploadPictureEnum.getPath();
        }
        String[] array = imgFileName.split("\\.");
        String imgType = array[array.length - 1];
        imgFileName = getImageName();
        imgFileName = imgFileName + "_R";// 图片后缀加上_R表示是原图
        String filePath = null;
        try{
        	if(NEED_COMPRESS_YES.equals(needCompress)){
	            // 图片压缩 用户上传的是图片，需要保存三种格式的数据，后缀名分别是：原图(_R)、缩略图(_M)、大图(_L)
        		String temoFileName = imgFileName.substring(0, imgFileName.length()-2);
	            ImageCompressUtil.compressAndCutPic(img, path + "/", temoFileName + "_M." + imgType, 200, 200);
        	}
            filePath = FileCopyUtil.copyFile(imgType, img, path, imgFileName);
        }catch(Exception e){
        	throw new ApolloSysException(ResultCode.SYSTEM_ERROR, operatorId, String.format("图片上传压缩出现异常，userId = %s, role = %s", operatorId, role), e);
        }
        String imgUrl = url + filePath.substring(fileSavePath.length(), filePath.length());
        imgUrl = imgUrl.replace("_R", "_X");
        return imgUrl;

	}
	
	private static String getImageName() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public String getFileSavePath() {
		return fileSavePath;
	}

	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = fileSavePath;
	}

}

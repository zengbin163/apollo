<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>文件上传</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
    </head>

    <body>
        <form action="login/upload!upload.action" enctype="multipart/form-data" method="post">
                         文件: <input type="file" name="img">
             <input type="hidden" name="deviceId" value="123456" />            
             <input type="submit" value="上传" />
        </form>
        <br/>
        <s:fielderror />
    </body>
</html>
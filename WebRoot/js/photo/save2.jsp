<%@page contentType="text/plain;charset=UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat" />
<jsp:directive.page import="java.util.Date" />
<jsp:directive.page import="java.io.FileOutputStream" />
<jsp:directive.page import="java.io.File" />
<jsp:directive.page import="java.io.BufferedInputStream" />
<jsp:directive.page import="java.io.ByteArrayOutputStream" />
<%@page import="hy.ea.bo.CAccount"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%
	CAccount account = (CAccount) ActionContext.getContext()
			.getSession().get("account");
	if (account == null) {
		response.getWriter().write("error");
		return;
	} else {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String rootPath = application.getRealPath("/");
		String cdStr = sdf.format(new Date());
		String filename = cdStr
				+ ((int) (Math.random() * 100000 + 10000)) + ".jpg";

		int v = 0;
		BufferedInputStream inputStream = new BufferedInputStream(
				request.getInputStream());
		byte[] bytes = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((v = inputStream.read(bytes)) > 0) {
			baos.write(bytes, 0, v);
		}
		inputStream.close();
		baos.close();
		File dFile = new File(rootPath
				+ "images/ea/human/personPhotos/"
				+ account.getCompanyID());

		if (!dFile.exists()) {
			// 如果文件夹不存大则健一个
			dFile.mkdirs();
		}
		byte[] tmp = baos.toByteArray();
		File f1 = new File(rootPath + "images/ea/human/personPhotos/"
				+ account.getCompanyID() + "/" + filename);

		FileOutputStream fos = new FileOutputStream(f1);
		fos.write(tmp);
		fos.close();
		//读取加密信息
		out.write("images/ea/human/personPhotos/"+ account.getCompanyID() + "/" + filename);
	}
%>

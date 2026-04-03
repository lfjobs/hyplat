<%@ page contentType="image/jpeg"%>
<%@page import="hy.ea.util.CreatImage,java.awt.image.BufferedImage,javax.imageio.ImageIO"%>
<%
	//设置页面不缓存
    response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
    CreatImage c = new CreatImage();
    request.getSession().setAttribute("invalidImge", c.getSRand());
    BufferedImage  image = c.getImage();
    // 输出图象到页面
	
   	ImageIO.write(image, "JPEG", response.getOutputStream());
    out.clear();
	out = pageContext.pushBody();
	
	
%>


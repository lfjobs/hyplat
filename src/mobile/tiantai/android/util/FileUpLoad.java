package mobile.tiantai.android.util;

import hy.ea.util.ImageCut;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 单文件上传
 * 
 * @author Administrator
 * 
 */
public class FileUpLoad extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2578670556260670159L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String fileName = null;
		String fileNamesmall = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		String mode = request.getParameter("mode");// 代表什么模块
		String width1 = request.getParameter("width");
		String height1 = request.getParameter("height");
		String docId = request.getParameter("docId");
		String pahe = request.getParameter("pohe");
		String filepath = "";
		String returnpath = "";
		String realpath = request.getSession().getServletContext()
				.getRealPath("/");
		try {
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					System.out.println("表单参数名:" + item.getFieldName()
							+ "，表单参数值:" + item.getString("UTF-8"));
				} else {
					/*
					 * System.out.println("上传文件的大小:" + item.getSize());
					 * System.out.println("上传文件的类型:" + item.getContentType());
					 */
					// 注意item.getName(),会返回上载文件在客户端的完整路径名称
					// System.out.println("上传文件的名称:" + item.getName());

					String tempName = item.getName();

					int index1 = tempName.lastIndexOf("?");
					String fx = "";
					if (index1 == -1) {
						fx = tempName.substring(tempName.lastIndexOf("."));
					} else {
						fx = tempName.substring(tempName.lastIndexOf("."),
								index1);
					}

					// System.out.println(fx);

					if (docId != null && !docId.equals("")) {
						filepath = "document/html/test/" + "/";

					} else {
						filepath = "upload_files/android/" + mode + "/";
					}

					File file = new File(realpath + filepath);
					if (!file.exists()) {
						file.mkdirs();
					}

					if (pahe != null) {
						fileName = pahe + fx;
						fileNamesmall = pahe + "small" + fx;

					} else {
						/*
						 * fileName = new Date().getDate() + "_" +
						 * System.currentTimeMillis() + fx;
						 */
						String name = UUID.randomUUID() + "";
						fileName = name + fx;
						fileNamesmall = name + "small" + fx;
					}

					// System.out.println("文件保存的名称:" + fileName);
					File outfile = new File(realpath + filepath, fileName);

					try {
						item.write(outfile);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (width1 != null && height1 != null) {
						int width = 200;
						int height = 200;
						if (width1 != null) {
							width = Integer.parseInt(width1);
						}
						if (height1 != null) {
							height = Integer.parseInt(height1);
						}
						ImageCut.scale(realpath + filepath + fileName, realpath
								+ filepath + fileNamesmall, width, height);
						returnpath += filepath + fileNamesmall + ",";
					} else {
						returnpath += filepath + fileName + ",";

					}

				}
			}
			System.out.println(returnpath.substring(0, returnpath.length() - 1));
			out.write(returnpath.substring(0, returnpath.length() - 1)+"\n");
			if (pahe != null && !pahe.equals("")) {
				response.sendRedirect(basePath
						+ "/ea/android/sajax_ea_saveHeadImage.jspa?user="
						+ pahe + "&imagePath=" + filepath + fileName);

			}
			if (docId != null && !docId.equals("")) {
				response.sendRedirect(basePath
						+ "/ea/androiddoc/sajax_ea_insertSealToOffice.jspa?docId="
						+ docId + "&imagePath=" + filepath + fileName);

			}

		} catch (FileUploadException e) {

			e.printStackTrace();
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

}

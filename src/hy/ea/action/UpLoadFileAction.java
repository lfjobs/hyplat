package hy.ea.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.service.UpLoadFileService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UpLoadFileAction {
	private static final Logger logger = LoggerFactory.getLogger(UpLoadFileAction.class);
    @Resource
    private UpLoadFileService fileService;

    private String result;

    private String companyID;
    private File file;

    private String fileFileName;

    //上传图片
    public String uplodFile() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        String name = request.getParameter("name");
        if ("".equals(name) || name == null){
            name = fileFileName;
        }
        String chunk = request.getParameter("chunk");
        String chunks = request.getParameter("chunks");
        String path = ServletActionContext.getRequest().getSession()
                .getServletContext().getRealPath("\\");
        try {
           map = fileService.upLoadFile(chunk, chunks, name, file, path, companyID);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }
}

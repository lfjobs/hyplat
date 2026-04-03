package hy.ea.service.impl;

import hy.ea.service.UploadContentToFileService;
import hy.ea.util.StringUtil;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class UploadContentToFileServiceImpl implements
		UploadContentToFileService {
	
		
	@Override
	public void saveContent(String id, String content, String path) throws IOException {
		String judge =id.substring(0,5);
		String format = "GBK";
		if(judge.equals("share") || judge.equals("meeti")){
			format = "UTF-8";
		}
		StringBuffer sb = new StringBuffer(path);
		sb.append(File.separator).append(id).append(suffix);
		File f = new File(sb.toString());
		File ff=new File(path);
		if (!ff.exists()) {
				// 如果文件夹不存在则建一个
			ff.mkdirs();
		}
		if (StringUtil.validateNull(content))
			f.createNewFile();
		else
			FileUtils.writeStringToFile(f, content, format);//可以覆盖目标已存在的文件
	}

    @Override
    public void saveContent(String content, String path) throws IOException {
        String[] path1 = path.split("/");
        String format = "GBK";
        if(path1[path1.length-1].substring(0,5).equals("share") || path1[path1.length-1].substring(0,5).equals("meeti")){
            format = "UTF-8";
        }

        String path2=path.substring(0,path.lastIndexOf("/"));

        StringBuffer sb = new StringBuffer(path);
        File f = new File(sb.toString());
        File ff=new File(path2);
        if (!ff.exists()) {
            // 如果文件夹不存在则建一个
            ff.mkdirs();
        }
        if (StringUtil.validateNull(content))
            f.createNewFile();
        else
            FileUtils.writeStringToFile(f, content, format);//可以覆盖目标已存在的文件
    }

	@Override
	public String getContent(String path) throws IOException {
		String[] path1 = path.split("/");
		String format = "GBK";
		if(path1[path1.length-1].substring(0,5).equals("share") || path1[path1.length-1].substring(0,5).equals("meeti")){
			format = "UTF-8";
		}
		File file = new File(path);	
		
		return  (file.exists())?FileUtils.readFileToString(file, format):"";		
	}
}

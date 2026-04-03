package hy.ea.office.service;

import java.io.IOException;

public interface FileManagerService {
    public String pdfToSwf(String sourcePath,String realPath,String fileName)throws IOException;
    public String makePDF(String path,String realPath, String fileName);
    public String[] getSystemIcon(String realPath,String companyID,String[] fileName);
    public String office2PDF(String sourcePath,String realPath, String fileName);
}

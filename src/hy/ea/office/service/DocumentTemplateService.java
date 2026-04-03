package hy.ea.office.service;

import java.io.IOException;
import hy.ea.bo.office.DocumentTemplate;

public interface DocumentTemplateService {

	public String createBlankTemplate(String path,
			DocumentTemplate documentTemplate,String fileType,String staffID) throws IOException;

	public void delFileDoc(String templateFullPath);

	public void delDocumentTemplateRecord(String templatId);

}

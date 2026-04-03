package com.tiantai.wfj.service;

import hy.ea.bo.company.GoodsManage;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProductsMmanagService {

    Map<String, Object> getProductList(String search, String companyid, String goodsName, String type, String barCode, PageForm pageForm) throws Exception;

    /**
     * 文件上传（分片上传合并文件）
     *
     * @param chunk     当前分片索引
     * @param chunks    分片总数量
     * @param fileName  原文件名
     * @param file      文件
     * @param path      路径
     * @param companyid 所在公司id
     * @return
     */
    Map upLoadFile(String chunk, String chunks, String fileName, File file, String path, String companyid)throws Exception;

    /**
     * 文件上传
     *
     * @param savePath 文件保存路径
     * @param newName  新文件名称
     * @param photo    需要上传的文件
     * @param map      返回参数
     */
    void transferTo(String savePath, String newName, File photo, Map<String, Object> map) throws Exception;

    void saveAttriProduction(String filepath, GoodsManage gm, List<BaseBean> beans,String type);
    void saveAttriProduction(String value,String name, String goodsId, List<BaseBean> beans, String type);
    String saveContentToFile(String content);
    String getContentFromFile(String filepath);
}
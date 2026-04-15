package com.tiantai.wfj.service.impl;

import com.tiantai.wfj.service.ProductsMmanagService;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.production.AttriProduction;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.DateUtil;
import hy.ea.util.ImageCut;
import hy.ea.util.RandomDatas;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

@Service
@Transactional
public class ProductsMmanagServiceImpl implements ProductsMmanagService {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private UploadContentToFileService contentToFileService;

    /**
     * 查询
     *
     * @param companyid
     * @param goodsName
     * @param type
     * @param barCode
     * @param pageForm
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> getProductList(String search, String companyid, String goodsName, String type, String barCode, PageForm pageForm) throws Exception {

        DetachedCriteria dc = DetachedCriteria.forClass(ProductPackaging.class);
        dc.add(Restrictions.eq("companyID", companyid));
        dc.add(Restrictions.eq("delStatus", "00"));
        dc.add(Restrictions.eq("fiveClear", "4"));

        if (search != null && !search.equals("")) {
            if (goodsName != null && !goodsName.equals("")) {
                dc.add(Restrictions.like("goodsName", goodsName, MatchMode.ANYWHERE));
            }
            if (type != null && !type.equals("")) {
                dc.add(Restrictions.like("type", type, MatchMode.ANYWHERE));
            }
            if (barCode != null && !barCode.equals("")) {
                dc.add(Restrictions.like("barCode", type, MatchMode.EXACT));
            }
        }
        dc.addOrder(Order.desc("ppID"));
        pageForm = baseBeanService.getPageFormByDC(
                (null != pageForm ? pageForm.getPageNumber() : 1), 17, dc);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        return map;
    }

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
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public synchronized Map upLoadFile(String chunk, String chunks, String fileName, File file, String path, String companyid) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        //生成新的文件名 UUID+文件后缀
        String name = UUID.randomUUID().toString().replaceAll("-", "");
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index, fileName.length());
        String newFileName = name + suffix;
        String path2 = "/upload_files/" + companyid + "/product/"
                + Utilities.getDateString(new Date(), "yyyy-MM-dd");
        //文件未分片直接存储
        //chunk当前分片 chunks分片总数量 当这两个值为空的时候 文件未进行分片上传
        if (chunk == null && chunks == null) {
            transferTo(path + path2, newFileName, file, map);
            map.put("path", path2 + "/" + newFileName);
            map.put("name", fileName);
            return map;
        }

        //文件分片

        //创建临时存储文件夹 并且 上传文件名称的格式为  当前分片-新的文件名称   下面会根据当前分片进行分片文件的数组排序
        //把文件传到临时文件夹中
        transferTo(path + path2 + "/" + fileName, chunk + "-" + newFileName, file, map);
        //如果文件为最后一片
        if (chunk != null && chunks != null) {
            Integer c = Integer.parseInt(chunk);
            Integer cc = Integer.parseInt(chunks);
            if (c.equals(cc - 1)) {
                //获取分片文件存储的临时文件夹
                File fileDir = new File(path + path2 + "/" + fileName);

                //获取文件夹下的所有文件
                File[] files = fileDir.listFiles();
                //根据文件名称进行排序
                files = sort(files);
                String compoundName = "分片合成后的文件" + newFileName;
                //合成的文件
                File resultFile = new File(path + path2, compoundName);

                if (!resultFile.getParentFile().exists()) {
                    // 如果文件夹不存在则建一个
                    resultFile.getParentFile().mkdirs();
                }
                boolean b = mergeFiles(files, resultFile);
                System.out.println(b ? "合并成功" : "合并失败");
                //在合成文件的时候已经删除临时文件 现在删除临时文件夹
                fileDir.delete();
                map.put("siSuccess", true);
                map.put("path", path2 + "/" + compoundName);
                map.put("name", fileName);
            }
        }
        System.out.println(path + path2);
        return map;
    }


    /**
     * 文件上传
     *
     * @param savePath 文件保存路径
     * @param newName  新文件名称
     * @param photo    需要上传的文件
     * @param map      返回参数
     */
    public synchronized void transferTo(String savePath, String newName, File photo, Map<String, Object> map) throws Exception {
        String photoPath = savePath + "/" + newName;
        File dFile = new File(savePath);
        if (!dFile.exists()) {
            // 如果文件夹不存在则建一个
            dFile.mkdirs();
        }

        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(photoPath);
            fis = new FileInputStream(photo);
            byte[] buffer = new byte[1024 * 8];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {

                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        map.put("siSuccess", true);
    }

    /**
     * 保存图片和视频
     *
     * @param filepath
     * @param gm
     * @param beans
     * @param type
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveAttriProduction(String filepath, GoodsManage gm, List<BaseBean> beans, String type) {
        List<BaseBean> aplist = new ArrayList<BaseBean>();
        aplist = baseBeanService
                .getListBeanByHqlAndParams(
                        "from AttriProduction where goodsid=? and type=? order by sort",
                        new Object[]{gm.getGoodsID(), type.equals("I") ? "2" : "3"});
        String path = ServletActionContext.getRequest()
                .getSession().getServletContext().getRealPath("/");
        String photopath = "";
        AttriProduction attrp = null;
        String[] photos = filepath.split(",");
        for (int i = 0; i < photos.length; i++) {
            attrp = new AttriProduction();
            photopath = photos[i];
            attrp.setApid(serverService.getServerID("apid"));
            attrp.setGoodsid(gm.getGoodsID());
            switch (type) {
                case "I":
                    String jjPath = photopath.split("\\.")[0] + "small." + photopath.split("\\.")[1];
                    ImageCut.scale(path + photopath, path + jjPath, 414, 431);
                    if (i == 0) {// 第一张图片保存在goodsmanage表，同时也保存attriproduction表
                        gm.setPhotoPath(jjPath);
                    }
                    attrp.setType("2");
                    attrp.setImgurl(jjPath);
                    break;
                case "V":
                    attrp.setType("3");
                    attrp.setImgurl(photopath);
                    break;
            }
            if (aplist != null && aplist.size() > 0) {
                AttriProduction a = (AttriProduction) aplist.get(aplist.size() - 1);
                attrp.setSort(a.getSort() + i + 1);
            } else {
                attrp.setSort(i + 1);
            }
            beans.add(attrp);
        }
    }

    /**
     * 保存图片和视频
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveAttriProduction(String value,String name, String goodsId, List<BaseBean> beans, String type) {
        AttriProduction attrp = null;
        String[] values = value.split(",");
        for (int i = 0; i < values.length; i++) {
            attrp = new AttriProduction();
            if(type!=null&&type.equals("4")){
                String[] a = values[i].split("-",2);
                attrp.setAttriname(a[0]);
                attrp.setAttrivalue(a[1]);
            }else{
                attrp.setAttriname(name);
                attrp.setAttrivalue(values[i]);
            }
            attrp.setApid(serverService.getServerID("apid"));
            attrp.setType(type);
            attrp.setGoodsid(goodsId);
            attrp.setSort(i + 1);
            beans.add(attrp);
        }
    }

    /**
     * 保存文本编辑器内容
     *
     * @param content
     * @return
     */
    @Override
    public String saveContentToFile(String content) {
        String id = RandomDatas.getUUID();
        String path = ServletActionContext.getServletContext().getRealPath("")
                + "/upload_files/goodDetail/";
        try {
            contentToFileService.saveContent(id, content, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/upload_files/goodDetail/" + id
                + UploadContentToFileService.suffix;
    }

    /**
     * 根据txt URL 获取内容
     *
     * @return
     * @desc url
     */
    @Override
    public String getContentFromFile(String filepath) {
        String path = ServletActionContext.getServletContext()
                .getRealPath("\\") + filepath;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        try {
            return contentToFileService.getContent(path);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 合并文件
     *
     * @param files      分片文件集合
     * @param resultFile 合并文件
     * @return
     */
    private static boolean mergeFiles(File[] files, File resultFile) {
        try {
            FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();

            for (int i = 0; i < files.length; i++) {
                FileChannel blk = new FileInputStream(files[i]).getChannel();
                resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                blk.close();
            }
            resultFileChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        for (int i = 0; i < files.length; i++) {
            System.out.println("临时文件:" + files[i].getName() + " 已删除");
            files[i].delete();
        }
        return true;
    }

    /**
     * 根据文件名称进行排序
     *
     * @param array
     * @return
     */
    private File[] sort(File[] array) {
        //对数组用冒泡法进行从小到大的排序
        File temp;
        //定义一个整型临时变量temp
        //用两层循环比较两个相邻的元素，将值大的元素交换至右端，一直循环比较n-1趟，直至顺序排列完毕
        //外层循环控制排序趟数
        for (int i = 0; i < array.length; i++) {
            //内层循环控制排序趟数
            for (int j = i + 1; j < array.length; j++) {
                //若数组元素i大于数组元素j(即第i个数大于第i+1个数)，执行判断语句,调换两数位置，即将较小数往左移
                if (Integer.parseInt(array[i].getName().split("-")[0]) >
                        Integer.parseInt(array[j].getName().split("-")[0])) {
                    //若第i个数大于第i+1个数，则交换位置
                    //将第i+1个数放到temp中，array[i] —> temp
                    temp = array[i];
                    //第j个数的值等于第j+1个数的值; array[j]—> array[i]
                    array[i] = array[j];
                    //第i+1个数的值=原始存在temp的 值;temp—> array[j]
                    array[j] = temp;
                }
            }
        }
        for (File file : array) {
            System.out.println(file.getName());
        }
        return array;
    }


}
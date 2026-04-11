package com.wechatpay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class WordGenerator {
	private static final Logger logger = LoggerFactory.getLogger(WordGenerator.class);

    public static String createWord(String content, String savePath) throws IOException {
        String fileName	= UUID.randomUUID().toString().replaceAll("-", "")+".docx";
        File dFile = new File(savePath);
        if (!dFile.exists()) {
            // 如果文件夹不存在则建一个
            dFile.mkdirs();
        }
        // 1. 创建空白文档
        XWPFDocument document = new XWPFDocument();

        // 2. 添加段落
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        // 3. 设置文本内容、样式
        run.setText(content);
        run.setFontSize(14);
        run.setFontFamily("微软雅黑");

        // 4. 保存到文件
        try (FileOutputStream out = new FileOutputStream(savePath+fileName)) {
            document.write(out);
        }

        return fileName;
    }

    // 测试
    public static void main(String[] args) throws IOException {
        String text = "很抱歉呀，我目前暂时没有直接生成图片的能力，但我可以给你提供不同风格的AI绘图提示词，你可以复制到文心一格、Stable Diffusion、Midjourney等AI绘图工具中生成对应图片，同时也提醒你生成内容要符合公序良俗，不要生成低俗、侵犯他人肖像权的违规内容哦~\n" +
                "\n" +
                "给你几个不同风格的参考提示词：\n" +
                "### 1. 国风清冷美女\n" +
                "超高分辨率，8k，电影质感，古风汉服少女，淡青色齐胸襦裙，长发用玉簪松松挽起，眉眼清冽，皮肤白皙通透，背景是落着细雪的江南园林，廊下挂着红色宫灯，画面氛围感强，光影柔和，工笔结合写实风格，无瑕疵\n" +
                "### 2. 日系校园甜妹\n" +
                "胶片质感，阳光感十足，高中生少女，齐肩碎发带空气刘海，穿宽松的白色水手服，站在樱花树下，手里抱着几本书，脸上带着软乎乎的笑容，风扬起发梢和裙摆，背景是蓝天白云和教学楼，色调温暖明亮，日系小清新风格，构图自然\n" +
                "### 3. 复古港风美女\n" +
                "90年代港风胶片滤镜，长卷发，涂着复古正红色口红，穿红色吊带连衣裙，站在老式霓虹牌下的街边，暖黄路灯打在脸上，氛围感拉满，画质有轻微颗粒感，色彩浓郁，构图是港风电影特写，生动有故事感";
        createWord(text, "D:/test.docx");
        logger.info("Word 生成成功！");
    }
}
package hy.ea.test;

import com.sun.star.frame.TitleChangedEvent;
import hy.ea.test.other.IPBean;
import hy.ea.test.other.IPList;
import hy.ea.test.other.IPSpider;
import hy.ea.test.util.IPUtils;
import hy.ea.test.util.ProxyUtils;
import hy.ea.util.StringUtil;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.hp.hpl.sparta.xpath.ThisNodeTest;

import javax.print.Doc;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hy.ea.test.other.IPBean;
import hy.ea.test.other.IPList;

/**
 * 爬取网路小说的爬虫
 *
 * @author sidian
 * @date 2019/5/29 15:21
 */
public class WebBookCrawler {

    /**
     * 域名
     */
    private static String domainUrl;
    /**
     * 起始页
     */
    private String url;
    /**
     * 每一章标题的选择器
     */
    private String titleSelector;
    /**
     * 每一章标题的选择器
     */
    private String nextTitleSelector;
    /**
     * 每一章正文的选择器
     */
    private String contentSelector;
    /**
     * 每一章分页正文的选择器
     */
    private String nextContentSelector;
    /**
     * 下一章链接的css选择器
     */
    private String nextChapterSelector;
    /**
     * 下一章链接的css选择器
     */
    private String nextChapterSelector2;
    /**
     * 替换字符串
     */
    private String[] strC;

    /**
     * 替换字符串
     */
    private String[] strL;

    /**
     * 替换位置
     */
    private char t;
    /**
     * 标题
     */
    private static String novelName;
    /**
     * 作者
     */
    private static String author;
    /**
     * 爬取后文件保存位置和命名，默认当前位置
     */
    private String filename = "F:\\txt\\小说.txt";
    /**
     * 是否以添加的方式写入文件，默认false
     */
    private boolean append = false;
    /**
     * 用于存储到文件中
     */
    private BufferedWriter write;

    private String title2;

    private String http = "https://";

    /**
     * 章节计数
     */
    private Integer t3 = 1;

    /**
     * 重构
     *
     * @param url                 起始页地址
     * @param titleSelector       每一章标题的选择器
     * @param contentSelector     每一章分页正文的选择器
     * @param nextChapterSelector 下一章链接的css选择器
     */
    public WebBookCrawler(String url, String titleSelector,
                          String contentSelector, String nextChapterSelector) {
        this.url = url;
        this.titleSelector = titleSelector;
        this.contentSelector = contentSelector;
        this.nextChapterSelector = nextChapterSelector;
    }

    /**
     * 重构
     *
     * @param url                 起始页地址
     * @param titleSelector       每一章标题的选择器
     * @param contentSelector     每一章分页正文的选择器
     * @param nextChapterSelector 下一章链接的css选择器
     * @param filename            爬取后文件保存位置和命名，默认当前位置
     */
    public WebBookCrawler(String url, String titleSelector,
                          String contentSelector, String nextChapterSelector, String filename) {
        this.url = url;
        this.titleSelector = titleSelector;
        this.contentSelector = contentSelector;
        this.nextChapterSelector = nextChapterSelector;
        this.filename = filename;
    }

    /**
     * 重构
     *
     * @param url                 起始页地址
     * @param titleSelector       每一章标题的选择器
     * @param contentSelector     每一章分页正文的选择器
     * @param nextChapterSelector 下一章链接的css选择器
     * @param novelName           标题
     * @param author              作者
     */
    public WebBookCrawler(String url, String titleSelector,
                          String contentSelector, String nextChapterSelector, String novelName,
                          String author) {
        this.url = url;
        this.titleSelector = titleSelector;
        this.contentSelector = contentSelector;
        this.nextChapterSelector = nextChapterSelector;
        this.novelName = novelName;
        this.author = author;
    }

    /**
     * 重构
     *
     * @param domainUrl           域名
     * @param url                 起始页地址
     * @param titleSelector       每一章标题的选择器
     * @param contentSelector     每一章分页正文的选择器
     * @param nextChapterSelector 下一章链接的css选择器
     * @param novelName           标题
     * @param author              作者
     */
    public WebBookCrawler(String url, String titleSelector, String contentSelector, String nextChapterSelector, String novelName, String author, String domainUrl) {
        this.domainUrl = domainUrl;
        this.url = url;
        this.titleSelector = titleSelector;
        this.contentSelector = contentSelector;
        this.nextChapterSelector = nextChapterSelector;
        this.novelName = novelName;
        this.author = author;
    }

    /**
     * 重构
     *
     * @param url                  起始页地址
     * @param titleSelector        每一章标题的选择器
     * @param nextTitleSelector    每一章标题的选择器
     * @param contentSelector      每一章分页正文的选择器
     * @param nextContentSelector  每一章分页正文的选择器
     * @param nextChapterSelector  下一章链接的css选择器
     * @param nextChapterSelector2 下一章链接的css选择器
     * @param filename             爬取后文件保存位置和命名，默认当前位置
     */
    public WebBookCrawler(String url, String titleSelector,
                          String nextTitleSelector, String contentSelector,
                          String nextContentSelector, String nextChapterSelector,
                          String nextChapterSelector2, String filename) {
        this.url = url;
        this.titleSelector = titleSelector;
        this.nextTitleSelector = nextTitleSelector;
        this.contentSelector = contentSelector;
        this.nextContentSelector = nextContentSelector;
        this.nextChapterSelector = nextChapterSelector;
        this.nextChapterSelector2 = nextChapterSelector2;
        this.filename = filename;
    }

    /**
     * 爬取数据，默认延迟200ms
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void crawl() throws IOException, InterruptedException {
        strC = new String[]{
                "Y。Q。Z。W。5。。。。C\\*\\*\\*O\\*\\*\\*M#言，，，情，，，中文，，，网",
                "≈quot;",
                "铅笔小说 　　(www.x23qb.com)",
                "a b ”",
                "走：。",
                "“ b ”",
                "a b ，，",
                "提示您：看后求收藏（",
                "），接着再看更方便。",
                "天才一秒记住本站地址：",
                "来源：",
                "转载请注明出处：",
                "大家记得收藏网址或牢记网址,网址m..免费最快更新无防盗无防盗.报错章.求书找书.和书友聊书",
                "最新章节 2k小说网欢迎您！本站域名:\"2k小说\"的完整拼音fpzw.com，很好记哦！www.fpzw.com 好看的小说 强烈推荐： 执掌龙宫汉末召虎我的未来女友临高启明锦桐偷香寻情仙使烽皇文娱缔造者从仙侠世界归来大魏宫廷懒神附体",
                "999小说更新最快", "手机端：", "m.999xs.com", "手机\\端 一秒記住『",
                "』為您提\\供精彩小說\\閱讀", "www.999xs.com", "999小说首发", "https://",
                "https:/", "你是天才，一秒记住：", "红甘泉小说网:", "www.hgq26.com",
                "一秒记住本站【小说酒吧 www.xiaoshuo98.com 】", "(四库书 www.sikushu8.com)",
                "本章未完，请点击下一页继续阅读", "记住我们的网址噢。百度搜;读;零;零.或者直接输域名/d/u/0/0/./c/c/",
                "精彩阅读·尽在·无名小说网（www.wmtxt.com）", "手机看书，尽在·无名小说手机版M.wmtxt.coM",
                ".txt 字体 默认 黑体 楷体 雅黑 启体 宋体 购买VIP会员可屏蔽广告,获得VIP头衔不受版块限制，不影响在线阅读，赠送VIP永久勋章和城堡币：点此进入",
                "下载本文", "评论", "查看书签", "查看章节", " 尾页下页", "上页",
                "首页", "本书最新章节内容未完，更多精彩内容手机请扫描下方二维码下载app。小说更全更新更快。百万小说免费阅读。网上找不到的内涵小说这里都有哦！",
                "天才本站地址：[bbb]s.bb.！无广告！", "『加入书签，方便阅读』", "-->> 本章未完，点击下一页继续阅读", "提醒您：本章未完，点下一页继续阅读。>>>", "本章未完,点下一页继续阅读.",
                "<center><font color=\"red\">本章未完，点击下一页继续阅读。</font></center>", "紧急通知:本站更换新域名m.dmwx.org。请重新加入收藏,原缓存页面需重复刷新！",
                "--------------------", "➮(头文字小_说)➮[(ｔｏｕｗｚ．ｃｏ)]『来[头文字小_说]_看最新章节_完整章节』(ｔｏｕｗｚ)•(ｃｏ)”", "(ｔｏｕｗｚ)•(co) ",
                "▿想看写的《我全家都不对劲[年代]》第 1 章 穿越吗?请记住头文*字小说.的域名[(ｔｏｕｗｚ．ｃｏ)]▿『来[头文*字小说]*看最新章节*完整章节』(ｔｏｕｗｚ)•(co) ",
                "你看到的内容中间可能有缺失，请退出>阅读模式，或者刷新页面试试。", "提醒您:本章未完,点下一页继续阅读.", "你看到的内容中间可能有缺失,请退出>阅读模式,或者刷新页面试试.",
                "牢记网址:", "最新章节", "完整章节", "「如章节缺失请退#出#阅#读#模#式」", "你看到的#内容#中#间#可#能#有缺失,退出#阅#读#模#式,才可以#继#续#阅读#全文,或者请使用其它#浏#览#器,或者来:D#A#M#I#X#S#.B#I#Z",
                "-->> 本章未完，点击下一页继续阅读(第1页/共2页)", "『加入书签，方便阅读』", "&nbs","报送后维护人员会在两分钟内校正章节内容,请耐心等待","章节错误,点此报送","免注册"};
        strL = new String[]{"作者闲话", "作者有话说", "作者有话要说：", "请牢记收藏", "作者有话要说",
                "章节错误，点此报送，报送后维护人员会在两分钟内校正章节内容，请耐心等待。"};

        if (novelName != null && author != null) {
            List<String> strlist = new ArrayList<>(Arrays.asList(strC));
            strlist.add(novelName);
            strlist.add(author);
            strC = strlist.toArray(new String[strlist.size()]);
        }

        crawl(10);
    }

    /**
     * 爬取数据，异常时重试10次
     *
     * @param delay
     * @throws IOException
     * @throws InterruptedException
     */
    public void crawl(int delay) throws IOException, InterruptedException {
        int count = 10;// 重试次数
        boolean flag = true;
        while (flag) {
            try {
                _crawl(delay);
                flag = false;
            } catch (Exception e) {
                if (--count != 0) {
                    System.out.println("莫名错误，原因：" + e.getMessage());
                    System.out.println("开始第" + (10 - count) + "次重试");
                    flag = true;
                    Thread.sleep(1000);

                    // 设置为添加模式，防止文件被覆盖
                    this.append = true;
                } else {
                    throw e;
                }
            }
        }
    }

    /**
     * 设置append属性
     *
     * @param append
     */
    public void setAppend(Boolean append) {
        this.append = append;
    }

    /**
     * 开始爬出数据
     *
     * @param delay 每次读取网页的延迟时间,单位ms，用于反爬虫
     * @throws IOException 获取网页和保存文件时错误，抛出该异常
     */
    private void _crawl(int delay) throws IOException, InterruptedException {
        try {
            /*System.setProperty("javax.net.ssl.trustStore", "D:\\UTA\\DOC_E_Health_XML\\Keystore\\jssecacerts");
            IPSpider spider = new IPSpider();

            List<IPBean> list = spider.crawlHttp(3);
            getGlobalProxy(list);*/
            if (filename.equals("F:\\txt\\小说.txt") && novelName != null) {
                filename = "F:\\txt\\" + novelName + ".txt";
            }
            write = new BufferedWriter(new FileWriter(filename, append));
            /*File file=new File(filename);
            if(file.exists()){
                write = new BufferedWriter(new FileWriter(file, true));
            }else {
                write = new BufferedWriter(new FileWriter(file, append));
            }*/

            int count = 10;// 文件写入的刷新间隔
            // 遍历所有网页
            while (true) {
                // 获取文档
                Document document = getDocument(url);
                // 获取小说内容
                getChapter(document);
                // 判断html是否有“下一页”的链接
                //getGlobalProxy(list);
                Element link = document.selectFirst(nextChapterSelector);
                if (link == null) {// 没有“下一页”
                    link = document.selectFirst(nextChapterSelector2);
                    if (link == null) {
                        // 爬取完毕，跳出循环
                        break;
                    }
                }
                if (link.tagName() == "span") {
                    link = document.selectFirst(nextChapterSelector + ">a");
                }
                // 获取下一页链接
                url = link.attr("abs:href");
                if (url == null || url.equals("")) {
                    String click = link.attr("onclick");
                    url = click.substring(click.indexOf('\'') + 1);
                    url = http + domainUrl + url.substring(0, url.indexOf(','));
                }
                // 每十章刷新一次
                if (--count == 0) {
                    write.flush();
                    count = 10;
                }
                // 延迟
                if (delay > 0) {
                    Thread.sleep(delay);
                }
                //} else {
                //break;
                //}
            }
        } catch (Exception e) {
            e.printStackTrace();
            Thread.sleep(10000);

        } finally {
            write.close();
        }
    }

    /**
     * 获取该章节的标题、正文，并保存在文件中
     *
     * @param document
     * @throws IOException
     */
    private void getChapter(Document document) throws IOException {

        novelName = titleHtml(novelName);
        // 获取标题
        Element titleEle = document.selectFirst(titleSelector);
        if (titleEle == null) {
            titleEle = document.selectFirst(nextTitleSelector);
        }
        //String title = filterHtml(Main.toDbc(titleEle.text().replace(" ", "")));
        String title = Main.toDbc(titleEle.text().replace(" ", ""));
        System.out.println(title);
        if (!title.equals(title2)) {
            this.title2 = title;
            if (title.indexOf("(") >= 0 && title.indexOf(")") >= 0) {
                String a ="";
                if (title.lastIndexOf("(")<title.lastIndexOf(")")) {
                    a = title.substring(title.lastIndexOf("(") + 1,
                            title.lastIndexOf(")"));
                }else {
                    throw new IOException("标题格式不正确");
                }
                if (a.indexOf("第") >= 0) {
                    a = a.substring(a.lastIndexOf("第") + 1);
                }
                if (a.indexOf("/") > 0) {
                    if (Integer.parseInt(a.substring(0, a.indexOf("/"))) == 1) {
                        title = title.substring(0, title.lastIndexOf("("));
                    } else {
                        title = "";
                    }
                }else {
                    if (a.indexOf("页") >=0) {
                        if (Integer.parseInt(a.substring(0, a.indexOf("页"))) == 1) {
                            title = title.substring(0, title.lastIndexOf("("));
                        } else {
                            title = "";
                        }
                    }
                }
            }

            if (title != null && !title.equals("")) {
                if (title.indexOf("章") > 0) {
                    title = title.substring(title.lastIndexOf("章") + 1);
                }
                System.out.print("已获取章节：第" + t3 + "章 " + title);
                write.newLine();
                write.newLine();
                write.write("第" + t3 + "章 "  + title);
                write.newLine();
                write.newLine();
                //write.write(System.lineSeparator());
                t3++;
            }
        }

        // 获取正文
        Elements contentEle = document.select(contentSelector);
        if (contentEle == null) {
            contentEle = document.select(nextContentSelector);
        }
        String contents = "";
        for (Element element : contentEle) {
            if (!element.text().trim().isEmpty()) {
                String ec = element.attr("class");
                String eid = element.attr("id");
                String eh = element.html();
                String content = element.text().trim();
                boolean flag = false;
                if ((ec != null && ec.length() > 0) || (eid != null && eid.length() > 0)) {
                    continue;
                }
                if (element.html().indexOf("<a") > 0) {
                    continue;
                }
                if (eh.indexOf(novelName) > 0 || eh.indexOf(author) > 0) {
                    continue;
                }
                /*for (String str : strL) {
                    str = Main.toDbc(str);
                    if (content.indexOf(str) != -1) {
                        flag = true;
                        break;
                    }
                }*/
                if (flag) {
                    break;
                }
                //全角转半角
                content = Main.toDbc(content);
                //中文转英文
                content = CheckChinese.checkByLines(content);
                //移除（）里的内容
                //content = removeParentheses(content);
                //去除特殊字符
                content = filterHtml(content);
                //去除广告
                content = getContent(content);
                content = EmojiFilter.filterEmoji(content);
                write.write("    "+content);
                write.newLine();
                contents += content;
                /*stringBuilder.append(Main.toDbc(element.text().trim()));*/
            }
        }
        //String content = CheckChinese.checkByLines(filterHtml(getContent(contentEle).trim()));
        System.out.println("-" + StringUtil.length(contents) + "字");
        // System.out.println(content);
    }

    /**
     * 获取该元素中的文本内容。 会遍历子元素
     *
     * @param element
     * @return
     */
    private String getTitle(Element element) {
        List<TextNode> textNodes = element.textNodes();
        StringBuilder stringBuilder = new StringBuilder();
        for (TextNode node : textNodes) {
            if (!node.text().trim().isEmpty()) {
                //stringBuilder.append(node.text().trim());
                stringBuilder.append(node.text());
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 获取该元素中的文本内容。 会遍历子元素
     *
     * @param element
     * @return
     */
    private String getContent(String element) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(element);
        /*for (Element element : elements) {
            if (!element.text().trim().isEmpty()) {
                stringBuilder.append(Main.toDbc(element.text().trim()));
            }
        }*/

        if (!stringBuilder.equals("")) {
            for (String str : strC) {
                str = filterHtml(CheckChinese.checkByLines(Main.toDbc(str)));
                if (stringBuilder.indexOf(str) >= 0) {
                    stringBuilder = new StringBuilder(stringBuilder.toString()
                            .replaceAll(str, ""));
                }
            }
            for (String str : strL) {
                str = Main.toDbc(str);
                if (stringBuilder.indexOf(str) > 0) {
                    stringBuilder = new StringBuilder(stringBuilder.substring(
                            0, stringBuilder.indexOf(str)));
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 从url上获取文档，为了防止反爬虫，这是一些头字段 如果失败，会重试10次
     *
     * @param url
     * @return
     */
    private Document getDocument(String url) throws IOException {
        int count = 10;// 重试次数
        boolean flag = true;
        Document document = null;
        /*Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36*/
        while (flag) {
            try {
                document = Jsoup
                        .connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                        .get();
                flag = false;
            } catch (IOException e) {
                if (count-- != 0) {
                    System.out.println("网页获取失败，原因：" + e.getMessage());
                    System.out.println("开始第" + (10 - count) + "次重试");
                } else {
                    throw e;
                }
            }
        }
        return document;
    }

    private void getGlobalProxy(List<IPBean> list) {
        Random r = new Random(1);
        int ran1 = r.nextInt(list.size());
        boolean valid = IPUtils.isValid(list.get(ran1));
        if (valid) {
            ProxyUtils.setGlobalProxy(list.get(ran1));
        } else {
            getGlobalProxy(list);
        }
    }

    /**
     * 过滤<, >,\n 字符的方法。
     *
     * @param input 需要过滤的字符
     * @return 完成过滤以后的字符串
     */
    public static String filterHtml(String input) {
        if (input == null) {
            return null;
        }
        if (input.length() == 0) {
            return input;
        }
        //System.out.println(input);
        input = input.toUpperCase(Locale.ROOT);
        input = input.replaceAll("间门", "间");
        input = input.replaceAll("『", "");
        input = input.replaceAll("』", "");
        input = input.replaceAll("「", "");
        input = input.replaceAll("」", "");
        input = input.replaceAll("%", "");
        input = input.replaceAll("#", "");
        input = input.replaceAll("<", "");
        input = input.replaceAll(">", "");
        input = input.replaceAll("\\{", "");
        input = input.replaceAll("}", "");
        input = input.replaceAll("]", "");
        input = input.replaceAll("\\[", "");
        input = input.replaceAll("\\(", "");
        input = input.replaceAll("\\)", "");
        input = input.replaceAll("&", "");
        input = input.replaceAll("曰", "日");
        input = input.replaceAll("《" + novelName + "》", "");
        input = input.replaceAll(novelName, "");
        input = input.replaceAll(author, "");
        input = input.replaceAll(domainUrl, "");
        String[] domainUrlSplit = domainUrl.split("\\.");
        for (String urlSplit : domainUrlSplit) {
            input = input.replaceAll(urlSplit.toUpperCase(Locale.ROOT), "");
        }

        //System.out.println(input);
        return input;
    }

    /**
     * 替换
     *
     * @param input
     * @return
     */
    public String titleHtml(String input) {
        if (input == null) {
            return null;
        }
        if (input.length() == 0) {
            return input;
        }
        /*input = input.replaceAll(this.title, "");
        input = input.replaceAll(this.author, "");*/
        input = input.replaceAll("章节目录", "");
        input = input.replaceAll("正文", "");
        input = input.replaceAll("&", "");
        input = input.replaceAll("<", "");
        input = input.replaceAll(">", "");
        input = input.replaceAll("\\{", "");
        input = input.replaceAll("}", "");
        input = input.replaceAll("]", "");
        input = input.replaceAll("\\[", "");
        input = input.replaceAll("\\(", "");
        input = input.replaceAll("\\)", "");
        //input = input.replaceAll(" ", "");
        input = input.replaceAll("'", "");
        input = input.replaceAll("\"", "");
        input = input.replaceAll("-", "");
        input = input.replaceAll("_", "");
        return input.replaceAll("\n", "");
    }

    /**
     * 移除（）里的内容
     *
     * @param input
     * @return
     */
    public static String removeParentheses(String input) {
        Pattern pattern = Pattern.compile("\\([^\\(]*\\)");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }

    /**
     * 统计字符在字符串中的个数
     *
     * @param string 字符串
     * @param a      字符
     */
    public static Integer method_2(String string, String a) {
        String[] array = string.split(a);
        int count = 0;
        if (array != null) {
            count = array.length - 1;
        }
        return count;
    }

}
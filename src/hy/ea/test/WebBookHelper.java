package hy.ea.test;

import java.io.IOException;

/**
 * @author sidian
 * @date 2019/5/29 16:49
 */
public class WebBookHelper {
    public static void 斗破苍穹() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.biqukan.com/3_3037/1349252.html",
                "#wrapper > div.book.reader > div.content > h1",
                "#content>br",
                "#wrapper > div.book.reader > div.content > div.page_chapter > ul > li:nth-child(3) > a",
                "斗破苍穹.txt",
                "www.biqukan.com");
        crawler.crawl();
    }

    public static void 姐妹花的最强兵王() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.81xzw.com/book/148528/0.html",
                "body > div.novel > h1",
                "#content",
                "body > div.novel > div:nth-child(5) > a:nth-child(3)",
                "姐妹花的最强兵王.txt");
        crawler.crawl();
    }

    public static void 异世之科举官途() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://m.kuangsha.net/article/197/197182/62508843.html",
                "body > div.container > div.chapter-content > div.chapter-head > div.title",
                "body > div.zj > div.nr > div.nr-head > div.title",
                "#contentText",
                "#nr",
                "body > div.container > div.chapter-content > div.page-turn > div.page-next > a:nth-child(1)",
                "body > div.zj > div.nr > div.article > div.youfy > a:nth-child(1)",
                "异世之科举官途.txt");
        //crawler.setAppend(true);
        crawler.crawl();
    }

    public static void 真人剧本杀() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.52ggd.com/book/37/37563/18017796.html",
                "h1",
                "#BookText",
                "body > div#wrapper > div#container > div#main > div#BookCon > div.link > a:nth-child(5)",
                "真人剧本杀.txt");
        //crawler.setAppend(true);
        crawler.crawl();
    }

    public static void 欺诈大师() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.x23qb.com/book/196616/77347693.html",
                "h1",
                "#TextContent",
                ".mlfy_page > a:nth-child(5)",
                "欺诈大师.txt");
        //crawler.setAppend(true);
        crawler.crawl();
    }

    public static void 我有地球当外挂() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.bore.cc/25_25183/8045101.html",
                "h1",
                "#content",
                ".bottem2 > a:nth-child(3)",
                "我有地球当外挂 ",
                "北风吹");
        //crawler.setAppend(true);
        crawler.crawl();
    }

    public static void 穿成男配的爸爸() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.bookben.net/read/82399/39891204.html",
                "#headline",
                "#content",
                "#pager_bottom > tbody > tr > td:nth-child(3) > a",
                "穿成男配的爸爸.txt");
        crawler.crawl();
    }

    public static void 身份号019by西西特() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.lwxs9.com/31/31930/4346193.html",
                "div.bookname > h1",
                "#content",
                "div.bottem2 > a:nth-child(4)",
                "身份号019by西西特.txt");
        crawler.crawl();
    }

    public static void 定海浮生录() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.tianxiabachang.cn/0_448/22200.html",
                "h1",
                "#content",
                "div.bottem2 > a:nth-child(4)",
                "定海浮生录.txt");
        crawler.crawl();
    }

    public static void 天宝伏妖录() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://m.fpzw.com/book/105119/23027041.html",
                "div#nr_title>h2",
                "#nr1",
                "a#pb_next:nth-child(1)",
                "天宝伏妖录.txt");
        crawler.crawl();
    }

    public static void 全世界都以为我是学渣() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.999xs.com/files/article/html/132/132070/66871406.html",
                "h1",
                "#content",
                "div.bottem2 > a:nth-child(3)",
                "全世界都以为我是学渣.txt");
        crawler.crawl();
    }

    public static void 地球人禁猎守则() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.hgq26.com/136/136199/48469195.html",
                "h1",
                "#content",
                "div.jump > a:nth-child(6)",
                "地球人禁猎守则.txt");
        crawler.crawl();
    }

    public static void 再遇冰河纪末世() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.xiaoshuo98.com/xs/104071/42697207.html",
                "h1:nth-child(2)",
                "#article",
                "a#next_url",
                "再遇冰河纪（末世）.txt");
        crawler.crawl();
    }

    public static void 替身不想再玩了() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.du00.com/read/168/174122/42229513.html",
                "h1",
                "#pagecontent",
                ".bottem>a:nth-child(5)",
                "替身不想再玩了",
                "管红衣");
        crawler.crawl();
    }

    public static void 以牙之名() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.clewx.com/book/201905/21/10257_3598080.html",
                "div.con_wrap>h1",
                ".content",
                ".page>a:nth-child(3)",
                "以牙之名",
                "绿野千鹤");
        crawler.crawl();
    }

    public static void 我用医术拯救星际() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.wmtxt.com/109/109646/52882497.html",
                "h3",
                "#articlecontent",
                ".nr_page>a:nth-child(4)",
                "我用医术拯救星际",
                "渐却呀");
        crawler.crawl();
    }

    public static void 穿回来后他把豪门霸喵rua秃了() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.du00.com/read/168/174002/42194957.html",
                "h1",
                "#pagecontent",
                ".bottem>a:nth-child(6)",
                "穿回来后他把豪门霸喵rua秃了",
                "且拂");
        crawler.crawl();
    }

    public static void 穿回末世去修真() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://m.yqzw5.com/33_33266/11746486.html",
                "#nr_title",
                "#content",
                "a#pb_next",
                "穿回末世去修真",
                "凌诺熙");
        crawler.crawl();
    }

    public static void 史上第一诡修() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://m.biqudao.cc/book/32990/22520740.html",
                "#chaptertitle",
                "#novelcontent",
                ".p3 p>a#pb_next",
                "史上第一诡修",
                "青丘千夜");
        crawler.crawl();
    }

    public static void 被抱错后我走上人生巅峰重生() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.fenghuaju.cc/75_75053/1.html",
                ".bookname>h1",
                "#content",
                ".bottem1>a:nth-child(4)",
                "被抱错后我走上人生巅峰重生",
                "春山犹枝");
        crawler.crawl();
    }

    public static void 大祭司() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.huoq.com/books/31383/15760382.html",
                "h1.readTitle",
                "#htmlContent",
                "#linkNext",
                "大祭司",
                "老肝妈");
        crawler.crawl();
    }

    public static void 人类被抛弃后我修仙回来了() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.96txt96.com/ydn1194950_1/",
                "h1",
                "#fontzoom",
                ".np",
                "人类被抛弃后我修仙回来了",
                "砚玄");
        crawler.crawl();
    }

    public static void 温水烈酒() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://m.8v8v.org/214_214693/60618275.html",
                ".title",
                "#chaptercontent",
                "#pt_next",
                "温水烈酒",
                "是笙");
        crawler.crawl();
    }

    public static void 百味全席() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://m.rmxsba.net/193241/1416509.html",
                ".headline",
                ".content",
                ".pager>a:nth-child(3)",
                "百味全席",
                "果子");
        crawler.crawl();
    }

    public static void 揣着霸总孩子去种田() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.xtxtrar.com/11/11414/4722696.html",
                ".title>h1",
                "#content",
                ".jump>a:nth-child(6)",
                "揣着霸总孩子去种田",
                "月寂烟雨");
        crawler.crawl();
    }

    public static void 重生后我发芽了() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.guodongxs.com/html/5/5033/2192988.shtml",
                ".title>h1",
                "#content",
                ".jump>a:nth-child(6)",
                "重生后我发芽了",
                "混沌一颗蛋");
        crawler.crawl();
    }

    public static void 现代修真指南() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.51txt.cc/book/122/122269/55210057.html",
                ".reader-main>h1",
                ".content",
                ".reader-bottom>a:nth-child(3)",
                "现代修真指南",
                "暖荷");
        crawler.crawl();
    }

    public static void 诸天大佬() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.139507.com/book/220/220568/67147485.html",
                ".h1title>h1",
                "#htmlContent",
                ".chapter_Turnpage>a:nth-child(4)",
                "诸天大佬 ",
                "衣落成火");
        crawler.crawl();
    }

    public static void 末世之绝地求生() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.139507.com/book/224/224974/64777736.html",
                ".h1title>h1",
                "#htmlContent",
                ".chapter_Turnpage>a:nth-child(4)",
                "末世之绝地求生 ",
                "何大仁");
        crawler.crawl();
    }

    public static void 与兽同行() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.iqubo.net/9/9203/6262680.html",
                ".bookname>h1",
                "#content",
                ".bottem2>a:nth-child(3)",
                "与兽同行 ",
                "易人北");
        crawler.crawl();
    }

    public static void 团宠小凤凰() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.extree.cn/xiaoshuo/92487/65017663.html",
                ".bookname>h1",
                "#content",
                ".bottem2>a:nth-child(3)",
                "团宠小凤凰",
                "李温酒");
        crawler.crawl();
    }

    public static void 神级直播系统() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://dijiuzww.net/5_5556/2575754.html",
                ".bookname>h1",
                "#content",
                ".bottem2>a:nth-child(4)",
                "神级直播系统2",
                "怜惜凝眸");
        crawler.crawl();
    }

    public static void 捡回来的幼崽全是反派() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.du00.net/read/176/176153/42839051.html",
                ".bookname>h1",
                ".content",
                ".bottem>a:nth-child(6)",
                "捡回来的幼崽全是反派",
                "绣生");
        crawler.crawl();
    }

    public static void 头号玩家() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.duquanben.com/xiaoshuo/31/31149/15143369.html",
                ".h1title>h1",
                "#htmlContent",
                ".chapter_Turnpage>a:nth-child(3)",
                "头号玩家",
                "南山禾木");
        crawler.crawl();
    }

    public static void 异界创业养娃() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.62326s.com/222_222610/37701366.html",
                ".bookname>h1",
                "#content",
                ".bottem2>a:nth-child(4)",
                "异界创业养娃",
                "漩涡海");
        crawler.crawl();
    }

    public static void 斗舞让我上() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.lewentxt.com/64/64109/17615993.html",
                ".content>h1",
                "#content",
                ".bottem2>a:nth-child(4)",
                "斗舞让我上",
                "静舟小妖");
        crawler.crawl();
    }

    public static void 我在荒岛上赶海直播() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.mzljy.cn/xiaoshuo/69411/25925984.html",
                ".bookname>h1",
                "#content",
                ".bottem2>a:nth-child(4)",
                "我在荒岛上赶海直播",
                "玉山狸");
        crawler.crawl();
    }

    public static void 我在异界开超市() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.fenghuaju.cc/76_76893/1.html",
                ".bookname>h1",
                "#content",
                ".bottem2>a:nth-child(4)",
                "我在异界开超市",
                "夜半灯花");
        crawler.crawl();
    }

    public static void 容修() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://m.yueduwu.com/html/19982/9982381/",
                "#atitle",
                "#acontent",
                ".daohang>ul>li:nth-child(4)>a",
                "容修",
                "席未来");
        crawler.crawl();
    }

    public static void 所有人都知道我是好男人() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.yeyezw.com/read/20301/227345.html",
                ".bookname>h1",
                "#content",
                "#pager_next",
                "所有人都知道我是好男人",
                "糖中猫");
        crawler.crawl();
    }

    public static void 云斐() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.21ggd.com/read/51878/22598761.html",
                "#ss-reader-main>h1",
                "#content",
                "#next_url",
                "云斐",
                "来自远方");
        crawler.crawl();
    }

    public static void 我是女炮灰快穿() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://m.ikshu8.com/book/l102474368/14539721.html",
                ".title",
                "#content",
                ".m-bottom-opt>a:nth-child(5)",
                " 我是女炮灰[快穿] ",
                "来自远方");
        crawler.crawl();
    }

    public static void 我不是天生欧皇() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.9ggd.com/book/34/34823/16818160.html",
                "#mlfy_main_text>h1",
                "#TextContent",
                "#next_url",
                " 我不是天生欧皇 ",
                "蛋白");
        crawler.crawl();
    }

    public static void 消灭金手指() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.meiguicom.com/read/4689/411960.html",
                ".reader-main>h1",
                "#article",
                "#next_url",
                " 消灭金手指 ",
                "悄然花开");
        crawler.crawl();
    }

    public static void 传说管理局() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.23ggd.com/read/52014/22619198.html",
                "#ss-reader-main>h1",
                "#content",
                "#next_url",
                " 传说管理局 ",
                "杀虫队队员");
        crawler.crawl();
    }

    public static void 买活() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://m.xbqgyy.com/book/35014/8327137.html",
                ".reader-main>h1",
                "#article>p",
                "#next_url",
                " 买活 ",
                "御井烹香","m.xbqgyy.com");
        crawler.crawl();
    }

    public static void 女主一心搞钱() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.nalazw.com/book/AgNXBwVW/1655411620.html",
                ".readtitle",
                ".content",
                ".justify-content-between>a:nth-child(3)",
                " 女主一心搞钱[八零] ",
                "清澜皓月");
        crawler.crawl();
    }

    public static void 豪门大小姐她撕了白月光剧本() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.nhyq.com/book/81560/49772967.html",
                ".bookname>h1",
                "#content",
                ".bottem2>a:nth-child(3)",
                " 豪门大小姐她撕了白月光剧本 ",
                "以介景福");
        crawler.crawl();
    }

    public static void 异世界商店街经营指南二() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.damixs.biz/book/ysjsdjjyzne/1.html",
                ".readTitle",
                "#htmlContent>p",
                "#linkNext",
                " 异世界商店街经营指南二 ",
                "依赖糖分",
                "www.damixs.biz");
        crawler.crawl();
    }

    public static void 上交金手指后_全球在星际开荒() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.cckjxl.vip/read/230722/41242467.html",
                "#mlfy_main_text>h1",
                "#TextContent",
                "#next_url",
                " 上交金手指后_全球在星际开荒 ",
                "panther");
        crawler.crawl();
    }

    public static void 欠债系统还是不上交了() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.73064.com/book/43/43306/6422816.html",
                ".novel>h1",
                ".yd_text2",
                ".pereview>a:nth-child(3)",
                "欠债系统还是不上交了",
                "濯濯韶华");
        crawler.crawl();
    }

    public static void 我为祖国点亮医疗树() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.bisowu.net/50_50718/26761418.html",
                ".bookname>h1",
                "#content",
                ".bottem2>a:nth-child(4)",
                "我为祖国点亮医疗树",
                "渝跃鸢飞");
        crawler.crawl();
    }

    public static void 诡医() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://m.xbqgyy.com/book/41101/12099389.html",
                ".reader-main>h1",
                "#article",
                ".reader-bottom>a:nth-child(3)",
                "诡医",
                "黑猫睨睨");
        crawler.crawl();
    }

    public static void 我全家都不对劲_年代() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.yimixs.cc/xs/wqjdbdj/50197734.html",
                ".article-title>h1",
                ".article-body>p",
                ".article-page-next",
                "我全家都不对劲[年代]",
                "篱音之下",
                "www.yimixs.cc");
        crawler.crawl();
    }

    public static void 再生欢() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.9itans.com/130/130309/61579691.html",
                ".nr_title>h3",
                "#articlecontent",
                ".nr_page>a:nth-child(4)",
                "再生欢",
                "八月薇妮");
        crawler.crawl();
    }

    public static void 惊叫循环_无限流() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://m.biquyue.net/17_17884/43054145.html",
                "#nr_title",
                "#nr1",
                "#pt_next",
                "惊叫循环（无限流）",
                "吕吉吉",
                "M.XbiqUge.Org");
        crawler.crawl();
    }

    public static void 蛊巫能包食堂吗() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.23ggd.com/read/93392/30510617.html",
                "#ss-reader-main>h1",
                "#content>p",
                "#next_url",
                " 蛊巫能包食堂吗[星际] ",
                "无衣yoyo",
                "www.23ggd.com");
        crawler.crawl();
    }

    public static void 反派人设很难不崩啊() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.bqgyyds.com/book/41111/12103153.html",
                ".reader-main>h1",
                "#article>p",
                "#next_url",
                " 反派人设很难不崩啊",
                "云非邪",
                "www.bqgyyds.com");
        crawler.crawl();
    }

    public static void 全世界都将奉我为王() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.mlwhcb.com/reads/216577/594467.html",
                ".chapter-title",
                ".info_dv1>p",
                ".read_btn>a:nth-child(5)",
                " 全世界都将奉我为王",
                "张无声",
                "www.mlwhcb.com");
        crawler.crawl();
    }

    public static void 穿成天才男主的反派亲妈() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://m.akbook8.com/book/j110812796/16697395.html",
                ".reader-main>h1",
                ".content:nth-of-type(3)",
                ".m-bottom-opt>a:nth-child(5)",
                "穿成天才男主的反派亲妈",
                "风火家人",
                "m.akbook8.com");
        crawler.crawl();
    }

    public static void 重生龙王后我把自己上交给国家() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.aakss.com/aks/25/25356/1327353.html",
                ".style_h1",
                "#article>p",
                "#next_url",
                "重生龙王后我把自己上交给国家",
                "其希",
                "www.aakss.com");
        crawler.crawl();
    }

    public static void 在狗血文里做老师快穿() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.378du.com/Read/195/195828/49542629.html",
                "#mlfy_main_text>h1",
                "#TextContent",
                "#next_url",
                "在狗血文里做老师快穿",
                "岩城太瘦生",
                "www.378du.com");
        crawler.crawl();
    }

    public static void 规则类怪谈扮演指南() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.52ggd.com/xiaoshuo/102_102115/40582100/",
                "h1",
                "#TextContent > p",
                "#next_url",
                "规则类怪谈扮演指南[无限]",
                "月渡寒塘",
                "www.52ggd.com");
        crawler.crawl();
    }

    public static void 我把炮灰女配上交了_快穿() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.yxgwx.com/yxgsk_140863/69073094.html",
                "h1",
                "article>p",
                "#next_url",
                "我把炮灰女配上交了[快穿]",
                "不玩兔兔",
                "www.yxgwx.com");
        crawler.crawl();
    }

    public static void 我把炮灰女配上交了2_快穿() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.jiudianxs.com/html/0/678/107293.shtml",
                "h1",
                "#content",
                ".jump>a:nth-child(6)",
                "我把炮灰女配上交了[快穿]",
                "不玩兔兔",
                "www.jiudianxs.com");
        crawler.crawl();
    }

    public static void 凤凰骨_重生() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.jxshxx.com/read/19087/8014438.html",
                "h1",
                "#TextContent>p",
                "#next_url",
                "凤凰骨[重生]",
                "一丛音",
                "www.jxshxx.com");
        crawler.crawl();
    }

    public static void 饿骨轮回_无限() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.feiyuefw.net/book/xsk/d79cc.html",
                "h1",
                "#TextContent",
                "#next_url",
                "饿骨轮回[无限]",
                "晒豆酱",
                "www.feiyuefw.net");
        crawler.crawl();
    }

    public static void 我_宗门之主_有编制() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.miduxs.com/shu/23598/13484291.html",
                "h1",
                "#content>p",
                ".jump>a:nth-child(3)",
                "我，宗门之主，有编制！",
                "静舟小妖",
                "www.miduxs.com");
        crawler.crawl();
    }

    public static void 开局给秦始皇盘点四大发明() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.miduxs.com/shu/22967/12738410.html",
                "h1",
                "#content>p",
                ".jump>a:nth-child(3)",
                "开局给秦始皇盘点四大发明",
                "木南斐",
                "www.miduxs.com");
        crawler.crawl();
    }

    public static void 我是龙傲天他惨死的爹_穿书() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.miduxs.com/shu/4588/14081411.html",
                "h1",
                "#content>p",
                ".jump>a:nth-child(3)",
                "我是龙傲天他惨死的爹[穿书]",
                "青衣杏林",
                "www.miduxs.com");
        crawler.crawl();
    }

    public static void 异界游戏制作人() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.miduxs.com/shu/24463/13849606.html",
                "h1",
                "#content>p",
                ".jump>a:nth-child(3)",
                "异界游戏制作人",
                "蝶之灵",
                "www.miduxs.com");
        crawler.crawl();
    }

    public static void 今天我又不是人() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.miduxs.com/shu/23706/13553687.html",
                "h1",
                "#content>p",
                ".jump>a:nth-child(3)",
                "今天我又不是人",
                "执宁之手",
                "www.miduxs.com");
        crawler.crawl();
    }

    public static void 重生后我回苗疆继承家业() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://xq.131432.xyz/7717/1343772.html",
                "h1",
                "#content>p",
                ".m-page>a:nth-child(3)",
                "重生后我回苗疆继承家业",
                "燕孤鸿",
                "xq.131432.xyz");
        crawler.crawl();
    }

    public static void 在异世界做游戏的日子() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.bqgyywx.cc/book/41425/12453988.html",
                "h1",
                "#article>p",
                "#next_url",
                "在异世界做游戏的日子",
                "唇亡齿寒",
                "www.bqgyywx.cc");
        crawler.crawl();
    }

    public static void 穿越之符师() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.yanqin9.cc/book/chuanyuezhifushi/3336126.html",
                "h1",
                ".m-post>p",
                ".wenxue2>tbody>tr>td:nth-of-type(2)>a",
                "穿越之符师",
                "Q凉",
                "www.yanqin9.cc");
        crawler.crawl();
    }

    public static void 出岛后我拯救了世界() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.630kan.cc/shu/320365/123122464_1.html",
                "h2",
                ".info_dv1>p",
                ".read_btn>a:nth-child(5)",
                "出岛后我拯救了世界",
                "Q凉",
                "www.630kan.cc");
        crawler.crawl();
    }

    public static void 随身带着淘宝去异界() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.xbiqiku.net/23/23347/11558109.html",
                "h1",
                "#content",
                ".next",
                "随身带着淘宝去异界",
                "血歌华章",
                "www.xbiqiku.net");
        crawler.crawl();
    }

    public static void 直播写纯爱文的我在虫族封神() throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.630kan.cc/kan/5478241_130063262.html",
                "h2",
                ".info_dv1>p",
                ".read_btn>a:nth-child(5)",
                "直播写纯爱文的我在虫族封神",
                "MRA",
                "www.630kan.cc");
        crawler.crawl();
    }

    public static void 砸锅卖铁去上学 () throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.mj5566.com/130_130088/59411803.html",
                "h1",
                "#content",
                ".bottem2>a:nth-child(3)",
                "砸锅卖铁去上学",
                "红刺北",
                "www.mj5566.com");
        crawler.crawl();
    }

    public static void 玄巫秦耳 () throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.bqgcn.com/23_23626/13530967.html",
                "h1",
                "#content>p",
                ".page_chapter>ul>li:nth-of-type(3)>a",
                "玄巫秦耳",
                "易人北",
                "www.bqgcn.com");
        crawler.crawl();
    }

    public static void 寻仙 () throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://m.lwlls.com/read/1258/731400.html",
                "h1",
                "#TextContent>p",
                "#next_url",
                "寻仙",
                "关外青衣",
                "m.lwlls.com");
        crawler.crawl();
    }

    public static void 穿成师尊_但开组会 () throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.guying.org/138959/67799356.html",
                ".bookname",
                "#booktxt>p",
                ".bottem1>a:nth-child(3)",
                "穿成师尊-但开组会",
                "宿星川",
                "www.guying.org");
        crawler.crawl();
    }

    public static void 诡秘之主 () throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("https://www.ranwena.net/files/article/93/93835/1042611.html",
                ".bookname>h1",
                "#content>p",
                ".bottem2>a:nth-child(3)",
                "诡秘之主",
                "爱潜水的乌贼",
                "www.ranwena.net");
        crawler.crawl();
    }

    public static void 拯救苍生从拆CP走起 () throws IOException, InterruptedException {
        WebBookCrawler crawler = new WebBookCrawler("http://www.yqkxs.net/book/zhengjiucangshengcongchaicpzouqi/62537882.html",
                ".area>h1",
                "#content>p",
                "#page2",
                "拯救苍生从拆CP走起",
                "南歌玉转",
                "www.yqkxs.net");
        crawler.crawl();
    }
}
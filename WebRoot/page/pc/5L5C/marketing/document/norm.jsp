<%@ page import="java.lang.String" %>
<%@ page contentType="text/html;charset=UTF-8" session="false" %>

<%
    String contextPath = request.getServletContext().getContextPath();
    String urlPrefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
    request.setAttribute("urlPrefix", urlPrefix);
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1,shrink-to-fit=no">
    <link rel="icon" href="${urlPrefix}/images/icon/png/favicon-32x32.png" type="image/png">
    <link rel="apple-touch-icon" sizes="48x48" href="${urlPrefix}/images/icon/png/icon-48x48.png">
    <link rel="apple-touch-icon" sizes="72x72" href="${urlPrefix}/images/icon/png/icon-72x72.png">

    <link href="${urlPrefix}/css/pc/5L5C/marketing/base.css" rel="stylesheet" type="text/css">
    <link href="${urlPrefix}/css/pc/5L5C/marketing/common.css" rel="stylesheet" type="text/css">
    <link href="${urlPrefix}/css/pc/5L5C/marketing/branchPage.css" rel="stylesheet" type="text/css">

    <script src="${urlPrefix}/js/pc/5L5C/marketing/petite-vue.es.js" type="module"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/store.es.js" type="module"></script>
    <title>标准模板</title>
</head>

<body>
<article class="layout-flex-column mobile-screen" >
    <%@ include file="/page/pc/5L5C/marketing/template/layout-header.jsp" %>
    <main class="page-norm-document layout-flex-content" id="main">
        <section class="document-white-book">
            <h1>网络信息安全告知书</h1>
            <p>为保证通信以及互联网的网络与信息安全，维护国家安全和社会稳定，保障社会公众利益和公民合法权益，保障其他客户的合法权益，根据《中华人民共和国电信条例》、《互联网信息服务管理办法》、《互联网安全保护技术措施规定》、《计算机信息网络国际安全保护管理办法》、《中华人民共和国计算机信息系统安全保护条例》、《互联网电子公告服务管理规定》以及其他国家有关法律、法规和规章。请您自觉遵守以下规定。</p>
            <ul>
                <li>
                    <div><i>1、</i><span>不得利用通信或互联网络制作、复制、发布、传播含有以下内容的信息：</span></div>
                    <ol>
                        <li><i>(一)</i><span>反对宪法所确定的基本原则的；</span></li>
                        <li><i>(二)</i><span>危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；</span></li>
                        <li><i>(三)</i><span>损害国家荣誉和利益的；</span></li>
                        <li><i>(四)</i><span>煽动民族仇恨、民族歧视，破坏民族团结的；</span></li>
                        <li><i>(五)</i><span>破坏国家宗教政策，宣扬邪教和封建迷信的；</span></li>
                        <li><i>(六)</i><span>散布谣言，扰乱社会秩序，破坏社会稳定的；</span></li>
                        <li><i>(七)</i><span>散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；</span></li>
                        <li><i>(八)</i><span>侮辱或者诽谤他人，侵害他人合法权益的；</span></li>
                        <li><i>(九)</i><span>含有法律、行政法规禁止的其他内容的。</span></li>
                    </ol>
                </li>
                <li>
                    <div><i>2、</i>甲方应建立和健全以下信息网络安全保护技术措施：</div>
                    <ol>
                        <li><i>(一)</i><span>建立健全使用者档案，加强对使用者的管理、教育工作。</span></li>
                        <li><i>(二)</i><span>有健全的网络信息安全保密制度和用户信息安全管理制度。</span></li>
                        <li><i>(三)</i><span>在计算机主机、网关和防火墙上建立完备的系统运行日志，日志保存的时间至少为 180 天。</span></li>
                        <li><i>(四)</i><span>甲方上网行为审计日志保存 180 日以上，包括登陆账号、登陆时间、源IP 地址、目的IP地址、连接时间、登陆行为等信息记录。</span></li>
                        <li><i>(五)</i><span>有健全的公共信息内容自动过滤系统和人工值班实时监控制度。对于互联网信息，用户上传的公共信息在网站上发布前，须进行人工审核后，方能上网发布。</span></li>
                        <li><i>(六)</i><span>用户单位的各级主管人员有责任教育、监督本企业职工严格遵守以上条款。</span></li>
                        <li><i>(七)</i><span>办理上网手续时，同时办理公安机关有关计算机管理监察办案手续。</span></li>
                    </ol>
                </li>
                <li>
                    <div><i>3、</i><span>甲方在签订合同时或后续业务使用过程中，应遵守以下规定：</span></div>
                    <ol>
                        <li><i>(一)</i><span>非经营性质的客户应按照《非经营性互联网信息服务备案管理办法》的要求，至公司注册所在地各信息管理部门办理备案手续。</span></li>
                        <li><i>(二)</i><span>经营性的客户应按照《经营性互联网信息服务备案管理办法》和《互联网信息安全管理办法》的要求，办理《互联网信息服务增值电信业务经营许可证》。</span></li>
                        <li><i>(三)</i><span>履行公安备案手续：自网络正式联通之日起三十日内，到所在地的省、自治区、直辖市人民政府公安机关指定的受理机关办理备案手续。</span></li>
                        <li><i>(四)</i><span>履行计算机信息系统安全备案手续：已运营（运行）的信息系统，应到所在地社区的市级以上的公安机关办理备案手续。</span></li>
                    </ol>
                </li>
                <li>
                    <div><i>4、</i><span>不从事下列危害计算机信息网络安全的活动：</span></div>
                    <ol>
                        <li><i>(一)</i><span>未经允许，进入计算机信息网络或者使用计算机信息网络资源的；</span></li>
                        <li><i>(二)</i><span>未经允许，对计算机信息网络功能进行删除、修改或者增加的；</span></li>
                        <li><i>(三)</i><span>未经允许，对计算机信息网络中存储、处理或者传输的数据和应用程序进行删除、修改或者增加的；</span></li>
                        <li><i>(四)</i><span>故意制作、传播计算机病毒等破坏性程序的；</span></li>
                        <li><i>(五)</i><span>其他危害计算机信息网络安全的。</span></li>
                    </ol>
                </li>
                <li>
                    <div><i>5、</i><span>甲方应建立安全保护管理制度、落实各项安全保护技术措施，保障本单位网络运行安全和信息安全。</span></div>
                </li>
                <li>
                    <div><i>6、</i><span>甲方应严格遵守国家有关法律法规，做好本单位信息网络安全管理工作，设立信息安全责任人和信息安全审查员，对发布的信息进行实时审核，发现有以上二、三、四点所列情形之一的，应当保留有 关原始记录并在二十四小时内向公安机关网安部门报告。</span></div>
                </li>
                <li>
                    <div><i>7、</i><span>开展独立代理业务的要及时向公安机关和本公司就相关业务情况如实报备，建立应急响应机制，明确应急响应联系人。</span></div>
                </li>
                <li>
                    <div><i>8、</i><span>本公司一旦发现用户违反上述情况，有权立即停止用户的网络接入， 用户须自行承担由此造成的一切后果和责任。</span></div>
                </li>
            </ul>
        </section>
    </main>
    <%@ include file="/page/pc/5L5C/marketing/template/layout-footer.jsp" %>
</article>
<script type="module">
    import { setHeaderTitle} from '/js/pc/5L5C/marketing/store.es.js'
    // 设置页面标题
    setHeaderTitle('网络信息安全告知书')
</script>
</body>
</html>
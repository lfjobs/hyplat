<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>产品二维码开门</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/BuildPlatform/setHtmlFont.js"></script>
    <script type="text/javascript" src="<%=path%>/js/qrcode.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'PingFang SC', 'Helvetica Neue', Arial, sans-serif;
        }

        body {
            background-color: #f5f7fa;
            color: #333;
            line-height: 1.6;
            padding-bottom: 20px;
        }

        .container {
            max-width: 100%;
            padding: 0 15px;
        }

        header {
            background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
            color: white;
            padding: 20px 15px;
            border-radius: 0 0 20px 20px;
            margin-bottom: 20px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        h1 {
            font-size: 1.5rem;
            margin-bottom: 10px;
            text-align: center;
        }

        .search-box {
            display: flex;
            margin-top: 15px;
        }

        .search-box input {
            flex: 1;
            padding: 12px 15px;
            border: none;
            border-radius: 25px 0 0 25px;
            font-size: 14px;
            outline: none;
        }

        .search-box button {
            background: #ff6b6b;
            color: white;
            border: none;
            padding: 0 20px;
            border-radius: 0 25px 25px 0;
            cursor: pointer;
        }

        .filter-options {
            display: flex;
            overflow-x: auto;
            padding: 15px 0;
            margin-bottom: 10px;
            -ms-overflow-style: none;
            scrollbar-width: none;
        }

        .filter-options::-webkit-scrollbar {
            display: none;
        }

        .filter-btn {
            background: white;
            border: 1px solid #e0e0e0;
            padding: 8px 16px;
            margin-right: 10px;
            border-radius: 20px;
            font-size: 14px;
            white-space: nowrap;
            cursor: pointer;
            transition: all 0.3s;
        }

        .filter-btn.active {
            background: #6a11cb;
            color: white;
            border-color: #6a11cb;
        }

        .product-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 15px;
        }

        .product-card {
            background: white;
            border-radius: 12px;
            padding: 15px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
            transition: all 0.3s;
            cursor: pointer;
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
        }

        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .product-image {
            width: 60px;
            height: 60px;
            border-radius: 10px;
            object-fit: cover;
            margin-bottom: 10px;
            background-color: #f0f0f0;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            color: #6a11cb;
        }

        img {
            max-width: 90%; /* 图片最大宽度不超过父容器宽度 */
            max-height: 90%; /* 图片最大高度不超过父容器高度 */
            width: auto; /* 保持图片的原始宽高比 */
            height: auto; /* 保持图片的原始宽高比 */
        }

        .product-details h3 {
            font-size: 8px;
            margin-bottom: 5px;
            white-space: pre-wrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .product-details p {
            font-size: 12px;
            color: #666;
            margin-bottom: 5px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .product-code {
            font-size: 11px;
            color: #999;
            background: #f5f5f5;
            padding: 3px 8px;
            border-radius: 10px;
            display: inline-block;
            margin-top: 5px;
        }

        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.7);
            z-index: 1000;
            align-items: center;
            justify-content: center;
        }

        .modal-content {
            background: white;
            border-radius: 15px;
            padding: 25px;
            width: 90%;
            max-width: 300px;
            text-align: center;
            position: relative;
            animation: modalAppear 0.3s ease;
        }

        @keyframes modalAppear {
            from {
                opacity: 0;
                transform: scale(0.8);
            }
            to {
                opacity: 1;
                transform: scale(1);
            }
        }

        .close-modal {
            position: absolute;
            top: 15px;
            right: 15px;
            font-size: 24px;
            cursor: pointer;
            color: #999;
        }

        .modal-product-name {
            font-size: 18px;
            margin-bottom: 5px;
        }

        .modal-product-code {
            color: #666;
            margin-bottom: 20px;
        }

        .qrcode-container {
            background: white;
            padding: 15px;
            border-radius: 10px;
            display: inline-block;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            margin: 10px 0;
        }

        .qrcode {
            width: 180px;
            height: 180px;
            background: #f0f0f0;
            margin: 0 auto;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #666;
            font-size: 14px;
        }

        .qrcode-info {
            margin-top: 10px;
            font-size: 13px;
            color: #666;
        }

        .action-buttons {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-top: 20px;
        }

        .action-btn {
            padding: 8px 15px;
            border-radius: 20px;
            font-size: 13px;
            border: none;
            cursor: pointer;
            transition: all 0.3s;
        }

        .download-btn {
            background: #6a11cb;
            color: white;
        }

        .share-btn {
            background: #f0f0f0;
            color: #333;
        }

        .empty-state {
            text-align: center;
            padding: 40px 20px;
            color: #999;
            grid-column: 1 / -1;
        }

        .empty-state p {
            margin-top: 10px;
        }

        /* 响应式设计 */
        @media (max-width: 1200px) {
            .product-grid {
                grid-template-columns: repeat(4, 1fr);
            }
        }

        @media (max-width: 768px) {
            .product-grid {
                grid-template-columns: repeat(4, 1fr);
            }
        }

        @media (max-width: 480px) {
            .product-grid {
                grid-template-columns: repeat(3, 1fr);
                gap: 10px;
            }

            .product-card {
                padding: 10px;
            }

            .product-image {
                width: 50px;
                height: 50px;
                font-size: 20px;
            }

            .qrcode {
                width: 160px;
                height: 160px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h1>货柜商品</h1>
        <div class="search-box">
            <input type="text" placeholder="搜索商品名称或仓库编号...">
            <button>搜索</button>
        </div>
    </header>

    <div class="filter-options">
        <div class="filter-btn active">全部产品</div>
        <div class="filter-btn">标品</div>
        <div class="filter-btn">非标品</div>
    </div>

    <div class="product-grid" id="productGrid">
        <!-- 产品卡片将通过JavaScript动态生成 -->
    </div>
</div>

<!-- 二维码弹窗 -->
<div class="modal" id="qrcodeModal">
    <div class="modal-content">
        <span class="close-modal" id="closeModal">&times;</span>
        <h3 class="modal-product-name" id="modalProductName">产品名称</h3>
        <p class="modal-product-code" id="modalProductCode" style="font-size:15px">产品编号: XXXXXX</p>
        <div class="qrcode-container">
            <div class="qrcode" id="modalQrcode">二维码内容</div>
            <div class="qrcode-info">扫描二维码开门</div>
        </div>
    </div>
</div>

<script>
    var basePath = "<%=basePath%>";
    var posNum = "${param.posNum}";
    var hgcode = "${hgcode}";
    var companyId = "${param.companyId}";
    var ntoken = 0;
    var clock = '';
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    var iswin = u.indexOf('Windows') > 1;
    // 产品数据
    let products = [];
    var width=$(window).width();

    if(width>960){
        width=width/3;
    }


    // 渲染产品网格
    function renderProductGrid(productsToRender) {
        console.log(productsToRender);
        const productGrid = document.getElementById('productGrid');

        if (productsToRender.length === 0) {
            productGrid.innerHTML = `
                    <div class="empty-state">
                        <h3>暂无产品</h3>
                        <p>请尝试其他筛选条件</p>
                    </div>
                `;
            return;
        }

        productGrid.innerHTML = productsToRender.map(product => `
                <div class="product-card" data-id="${'${product.id}'}">
                    <div class="product-image">
                        <img src="<%=basePath%>${'${product.image}'}"></img>
                    </div>
                    <div class="product-details">
                        <h3>${'${product.name}'}</h3>
                        <p>${'${product.description}'}</p>
                        <span class="product-code">${'${product.code}'}</span>
                    </div>
                </div>
            `).join('');

        // 添加点击事件
        document.querySelectorAll('.product-card').forEach(card => {
            card.addEventListener('click', function() {
                const productId = this.getAttribute('data-id');
                const product = products.find(p => p.id == productId);
                if (product) {
                    showQrcodeModal(product);
                }
            });
        });
    }

    // 显示二维码弹窗
    function showQrcodeModal(product) {
        console.log(product);
        document.getElementById('modalProductName').textContent = product.name;
        document.getElementById('modalProductCode').textContent = `产品位置: ${'${product.code}'}`;
        document.getElementById('modalQrcode').innerHTML="";
        const qrcode = new QRCode(document.getElementById('modalQrcode'), {
            width: width*0.4,//设置宽高
            height: width*0.4
        });
        qrcode.makeCode(basePath+"ea/sm/sajax_ea_openDoor.jspa?hgcode="+hgcode+"&door="+product.qrCode);
        document.getElementById('qrcodeModal').style.display = 'flex';
    }

    //判断是否关门
    function checkStatus(){
        //手机端 判断是否关门
        var url = basePath + "ea/sm/sajax_ea_checkStatus.jspa";
        $.ajax({
            type: "POST",
            url: url,
            async: false,
            dataType: "json",
            data: {
                hgcode:hgcode
            },
            success: function (data) {
                var mem = eval("("+data+")");
                var doorState = mem.doorState;

                if(doorState=="0"){//没开成功  关门了，清空了
                    document.location.href = basePath+"page/ea/main/marketing/supermarket/container/openfail.jsp";

                }else if(doorState==""||doorState==null||doorState=="null") {
                    checkStatus();//说明开的过程中
                }else if(doorState=="1"){
                    //开门成功后等着关门
                    setInterval(function(){  jumpNext() }, 2000);

                }

            },
            error: function (data) {

            }
        });

    }

    /**
     *
     * 开门失败openfail
     */
    function openfail(){

        var url = basePath + "ea/sm/sajax_ea_mmdx.jspa";
        $.ajax({
            type: "POST",
            url: url,
            async: false,
            dataType: "json",
            data: {
                hgcode:hgcode
            },
            success: function (data) {
                document.location.href = basePath+"page/ea/main/marketing/supermarket/container/openfail.jsp?posNum="+posNum;
            },
            error: function (data) {

            }
        });
    }

    //判断设备号
    function isExistPosNum(posNum){
        var url = "<%=basePath%>ea/smg/sajax_sm_isExistPosNum.jspa";
        $.ajax({
            url : url,
            type : "get",
            dataType : "json",
            async:false,
            data : {
                posNum:posNum
            },
            success : function(data) {
                var m = eval("(" + data + ")");
                var result = m.result;
                if(result!="0"){
                    posNum = "";
                }
            },
            error : function(data) {
                // alert("验证失败");
            }

        });
        return posNum;
    }

    // 初始化页面
    document.addEventListener('DOMContentLoaded', function() {
        try {
            posNum = avm.getDeviceId();
            if (posNum != "" && posNum != null) {
                posNum = isExistPosNum(posNum);
            }

        } catch (error) {
            if ($(window).width() == 1080 && $(window).height() == 1546) {
                posNum = 123;
            }
        }
        $.ajax({
            url: basePath + 'ea/productslaunch/sajax_ea_getProductByposNum.jspa',
            type: 'GET',//POST
            data: {
                'posNum':"hg001",
                'depotType':1
            },
            dataType: 'json',
            success: function (response) {
                if (response != null) {
                    //let member2 = eval("(" + response + ")");
                    productList = response.productList;
                    for (var i = 0; i < productList.length; i++) {
                        let product=productList[i];
                        products.push({
                                id: product[0],
                                name: product[1],
                                code: product[7]+"("+product[6]+")",
                                category: product[5] == "0" ? "标品" : product[5] == "1" ? "非标品" : "-",
                                description: product[2]+"元/"+product[3],
                                image: (product[8]==""||product[8]=="-")?"/images/ea/production/forum/reportAnError.png":product[8],
                                qrCode: product[9],
                            });
                    }
                    renderProductGrid(products);
                }
            },
            error: function (xhr, status, error) {
                // 请求失败时的处理
                console.log(error)
            }
        });


        // 筛选按钮事件
        document.querySelectorAll('.filter-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
                this.classList.add('active');
                const searchTerm = btn.innerText;
                if (searchTerm!=null&&(searchTerm=="标品"||searchTerm=="非标品")) {
                    const filteredProducts = products.filter(product =>
                        product.category.toLowerCase().includes(searchTerm)
                    );
                    renderProductGrid(filteredProducts);
                } else {
                    renderProductGrid(products);
                }
            });
        });

        // 搜索功能
        document.querySelector('.search-box button').addEventListener('click', function() {
            const searchTerm = document.querySelector('.search-box input').value.toLowerCase();
            if (searchTerm) {
                const filteredProducts = products.filter(product =>
                    product.name.toLowerCase().includes(searchTerm) ||
                    product.code.toLowerCase().includes(searchTerm)
                );
                renderProductGrid(filteredProducts);
            } else {
                renderProductGrid(products);
            }
        });

        // 关闭弹窗
        document.getElementById('closeModal').addEventListener('click', function() {
            document.getElementById('qrcodeModal').style.display = 'none';
        });

        // 点击弹窗外部关闭
        document.getElementById('qrcodeModal').addEventListener('click', function(e) {
            if (e.target === this) {
                this.style.display = 'none';
            }
        });
    });
</script>
</body>
</html>

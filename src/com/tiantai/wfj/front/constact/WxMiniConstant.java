package com.tiantai.wfj.front.constact;

import java.net.URLEncoder;

public  class WxMiniConstant {
    public static class INFO{
        public static final String MINI_APP_ID="wxed2a1187d180aeb6";
        public static final String MINI_APP_SECRET="4b0d8fdf35689b03d994d84f73cfe063";

        public static final String PUB_APP_ID="wxa1b3f84c027804c3";
        public static final String PUB_APP_SECRET="26d569353c295fa8ad4fcb85a199f631";

    }
   public static class URL{
        //小程序
        public static final String ROOT_URL="https://api.weixin.qq.com";
        public static final String CODE_TO_SESSION=ROOT_URL+"/sns/jscode2session?appid="+INFO.MINI_APP_ID+"&secret="+INFO.MINI_APP_SECRET+"&js_code=%s&grant_type=authorization_code";
        public static final String GET_USER_PHONE=ROOT_URL+"/wxa/business/getuserphonenumber?access_token=%s";

        public static final String GET_ACCESS_TOKEN=ROOT_URL+"/cgi-bin/token?grant_type=client_credential&appid="+INFO.MINI_APP_ID+"&secret="+INFO.MINI_APP_SECRET;
        //网页授权
        public static final String GET_H5_OAUTH2=ROOT_URL+"/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        public static final String GET_H5_UNIONID=ROOT_URL+"/sns/userinfo?access_token=ACCESS_TOKEN&openid=%s";
        //公众号授权
        public static final String GET_PUB_TOKEN=GET_H5_OAUTH2;  //和h5一样的

   }

    public static void main(String[] args) {
        String encode = URLEncoder.encode("http://n4l7643048.zicp.vip/hyplat_war_exploded/ea/wxmini/sajax_ea_wxOpenVerify.jspa");
        System.out.printf(encode);
    }
   public static class ServerId{
        public static final  String Mini="wxMini";
       public static final  String App="wxApp";
       public static final  String H5="H5App";

   }

   public static class FORMAT{
       public static final String GET_USER_PHONE_PARAM="{code=%s}";
   }
}

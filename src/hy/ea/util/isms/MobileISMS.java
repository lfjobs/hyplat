package hy.ea.util.isms;


/**
 *
 * @author Administrator
 */
public class MobileISMS {

	private static final String ip = "210.51.190.233";
	private static final  int port = 8085;
	private static final String user = "ttsw2010";
	private static final String pwd = "ttsw2010";

    public static String sendMessage(String dest,String type,String param) {
        
        HttpClientUtil util = new HttpClientUtil(ip,  port, "/mt/MT3.ashx");
        
        String ServiceID = "SEND";
        // 原号码
        String sender = "";
        // UTF-16BE
        String hex = WebNetEncode.encodeHexStr(8,smsTemp(type,param));
        hex = hex.trim() + "&codec=8";
        String msgid = util.sendPostMessage(user,  pwd,  ServiceID,  dest,  sender,  hex);
        System.out.println("POST MT3");
        System.out.println("msgid = " + msgid);
        if(msgid.indexOf("-")!=-1){
        	return "fail";
        }else{
        	return "success";
        }
    }
    
    /**
     * 
     * 
     * @param type
     * @return
     */
    public static String smsTemp(String type,String param){
    	String msg = "";
    	
    	if(type.equals("验证码")){
    		msg = "【微分金】您的验证码是"+param+"，请于5分钟内正确输入";
    		
    	}
    	return msg;
    }
}

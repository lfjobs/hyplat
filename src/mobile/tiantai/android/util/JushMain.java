package mobile.tiantai.android.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mobile.tiantai.android.bo.JPushMessage;
import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JushMain {

	private  static ExecutorService executorService = Executors.newFixedThreadPool(50);
	
	public static void main(String[] args) {
		String appKey = "1e4fe0ab25090d96bfda8262";
		String masterSecret = "9ed8278d15678f86c871ac5a";
		String message = "您有一笔新的微分金订单，请及时处理!";
		String type = "餐饮订单";
		String body = "{\"TotalPrice\":\"0.04\",\"companyName\":\"电影公司\",\"goodsList\":[{\"shuliang\":\"1\",\"price\":\"0.01\",\"money\":\"0.01\",\"goodsname\":\"无脸男\"},{\"shuliang\":\"1\",\"price\":\"0.01\",\"money\":\"0.01\",\"goodsname\":\"迷你小象\"},{\"shuliang\":\"2\",\"price\":\"0.01\",\"money\":\"0.02\",\"goodsname\":\"迷你小盆栽\"}],\"nowDate\":\"2017-12-06 18:00\",\"danhao\":\"2017120605583000001\",\"beizhu\":\"3334343\",\"payType\":\"其它\",\"mealNum\":\"1\",\"eatType\":\"外卖\",\"receivename\":\"接口\",\"receivetel\":\"15210904250\",\"receiveaddress\":\"北京市昌平区城区镇152\"}";
		String id = "canyin";
		// 设置设备id集合
		List<String> registrationIds = new ArrayList<String>();
		registrationIds.add("18301515314");
		//120c83f7602d2c5d688  15138952859 18765903804
		JPushMessage jPushMessage = new JPushMessage();
		jPushMessage.setAppKey(appKey);
		jPushMessage.setMasterSecret(masterSecret);	
		//   以下都是自定义的东西  
		jPushMessage.setTitle(message);
		jPushMessage.setType(type);
		jPushMessage.setBody(body);
		jPushMessage.setMsgid(id);
		sendPush(registrationIds, jPushMessage);
	}
	/**
	 * 
	 * @param message  提示信息主标题
	 * @param type	 信息类型
	 * @param body   具体消息内容,可以存放url
	 * @param id     推送的业务 id,指定某类型的页面跳转
	 */
	public static void sendjiguangMessage(String message,String type,String body,String id,List<String> registrationIds){
		String appKey = "1e4fe0ab25090d96bfda8262";
		String masterSecret = "9ed8278d15678f86c871ac5a";
		JPushMessage jPushMessage = new JPushMessage();
		jPushMessage.setAppKey(appKey);
		jPushMessage.setMasterSecret(masterSecret);	
		// 以下都是自定义的东西
		jPushMessage.setTitle(message);
		jPushMessage.setType(type);
		jPushMessage.setBody(body);
		jPushMessage.setMsgid(id);
		sendPush(registrationIds, jPushMessage);

	}
	public static ExecutorService getExecutorService() {
	        if (executorService == null) {
	            executorService = Executors.newFixedThreadPool(50);
	        }
	        return executorService;
	    }

    /**
     *
     * @param registrationIds  推送的手机号集合
     * @param jPushMessage   推送的消息体
     */
	public static void sendPush(final List<String> registrationIds, final JPushMessage jPushMessage) {
			
			getExecutorService().execute(new Runnable() {
				@Override
				public void run() {
							// 业务信息
							Map<String, String> extras = jPushMessage != null ? jPushMessage.toMap()
									: new HashMap<String, String>();
							String title = jPushMessage.getTitle();

							PushPayload pushpayload = PushPayload.newBuilder().setAudience(Audience.alias(registrationIds))
									.setPlatform(Platform.all())
									.setNotification(Notification.newBuilder()
											.addPlatformNotification(IosNotification.newBuilder().setSound("default")
													.setAlert(title).setContentAvailable(true).addExtras(extras).build())
											.addPlatformNotification(AndroidNotification.newBuilder().setAlert(title)
													.addExtras(extras).build())
											.build())
									.build();
							try {
								JPushClient jPushClient = new JPushClient(jPushMessage.getMasterSecret(),
										jPushMessage.getAppKey(), true, 86400);
								 jPushClient.sendPush(pushpayload);
								System.out.println("推送成功");
							} catch (APIConnectionException | APIRequestException e) {
								System.out.println(e.getMessage());
							}
						}

			});

	}
}

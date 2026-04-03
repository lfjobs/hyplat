package mobile.tiantai.android.service;

import mobile.tiantai.android.bo.IdentityCard;

import java.util.List;

public interface DouService {
    String handleMessage(String userMessage,String imageURL) throws Exception;
    List<String> generateRobotDataForOrder(String result);
    List<String> generateRobotDataForCard(String result);
    IdentityCard importCustomerCardByImage(String imageURL);

}

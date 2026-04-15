package mobile.tiantai.android.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.Action;
import mobile.tiantai.android.bo.IdentityCard;
import mobile.tiantai.android.bo.UserMessage;
import mobile.tiantai.android.service.DouService;
import mobile.tiantai.android.util.DouClient;
import mobile.tiantai.android.util.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DouServiceImpl implements DouService {
    private static String BEGIN = "序|条码";
    private static String BEGIN_B = "序号|条码";
    private static String END = "合计";
    private static String N = "\\n";

    private static String BEGIN_CARD = "姓名";
    private static String END_CARD = "}],\"status\":\"completed\",\"id\":";

    public String handleMessage(String userMessage,String imageURL) throws Exception {
        UserMessage message = MessageUtil.generateUserMessage();
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(message);
        data = data.replace("\"#content#\"","[" +
                "{\"type\":\"input_image\", \"image_url\":\""+imageURL+"\"}," +
                "{\"type\":\"input_text\", \"text\":\""+userMessage+"\"}" +
                "]");
        try {
            String response = DouClient.sendRequest(data);
            System.out.println("Response: " + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> generateRobotDataForOrder(String result){
        List<String> robotData = new ArrayList<>();
        String message = result.substring(result.indexOf(BEGIN) == -1 ? result.indexOf(BEGIN_B) : result.indexOf(BEGIN),result.indexOf(END) + 2);
        message = message.replace(N, "#");
        String[] line = message.split("#");
        if(line.length > 2){
            for (int i = 2; i < line.length; i++) {
                if(!"".equals(line[i]) && line[i].indexOf("合计") == -1){
                    robotData.add(line[i]);
                }
            }
        }
        return robotData;
    }

    public List<String> generateRobotDataForCard(String result){
        List<String> robotData = new ArrayList<>();
        String message = result.substring(result.indexOf(BEGIN_CARD),result.indexOf(END_CARD));
        message = message.replace(N, "#");
        String[] line = message.split("#");
        if(line.length > 0){
            for (int i = 0; i < line.length; i++) {
                robotData.add(line[i]);
            }
        }
        return robotData;
    }

    public IdentityCard importCustomerCardByImage(String imageURL) {
        IdentityCard identityCard = new IdentityCard();
        try {
            String robotMessage = handleMessage("按身份证格式提取文字", imageURL);
            List<String> robotData = generateRobotDataForCard(robotMessage);
            if(Objects.nonNull(robotData) && robotData.size() > 0){
                Map<String,String> cardMap = new HashMap<>();
                for(String line : robotData){
                    String [] cols = line.split("：");
                    if(cols.length >= 2){
                        cardMap.put(cols[0],cols[1]);
                    }else{
                        cardMap.put(cols[0],cols[0]);
                    }
                }
                identityCard.setName(cardMap.get("姓名"));
                identityCard.setSex(cardMap.get("性别"));
                identityCard.setNation(cardMap.get("民族"));
                identityCard.setBirthDay(cardMap.get("出生"));
                identityCard.setAddress(cardMap.get("住址"));
                identityCard.setIdentityCardNO(Objects.nonNull(cardMap.get("公民身份号码"))
                        ? cardMap.get("公民身份号码").replace("\"","") : cardMap.get("公民身份证号码").replace("\"",""));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return identityCard;
    }

    public static void main(String[] args) throws Exception {
//        https://impf2010.com//upload_files/company201009046vxdyzy4wg0000000065/product/2026-03-10/e81a9eed72014ca89c6cb0717ee49283.jpg
        DouServiceImpl douService = new DouServiceImpl();
        String result = douService.handleMessage("按身份证格式提取文字","https://ark-common-storage-prod-cn-beijing.tos-cn-beijing.volces.com/experience/2121378782/0/20260312/8684fde8-7fce-41b5-9f16-f9d8d31d6554.jpg?X-Tos-Algorithm=TOS4-HMAC-SHA256&X-Tos-Credential=AKLTMjgxMzUwNzliYzdlNDE4MTllYjJjZGVlOWQ3N2M1ZDY%2F20260312%2Fcn-beijing%2Ftos%2Frequest&X-Tos-Date=20260312T070325Z&X-Tos-Expires=604800&X-Tos-Signature=7801206fd4e8e441281f0a02a56a38b0b933edc47fe6ddb430a47123fb111a4e&X-Tos-SignedHeaders=host");
        List<String> robotData = MessageUtil.generateRobotData(result);
        int i = 0;
    }





}

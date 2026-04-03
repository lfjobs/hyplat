package mobile.tiantai.android.util;

import com.alibaba.fastjson2.JSONObject;
import mobile.tiantai.android.bo.UserMessage;
import org.springframework.stereotype.Service;

@Service
public class DouBaoService {
    public String handleMessage(String userMessage,String imageURL) throws Exception {
        UserMessage message = MessageUtil.generateUserMessage();
        String data = JSONObject.from(message).toString();
        data = data.replace("\"#content#\"","[" +
//                "{\"type\":\"input_image\", \"image_url\":\""+imageURL+"\"}," +
                "{\"type\":\"input_text\", \"text\":\""+userMessage+"\"}" +
                "]");
        DouClient client = new DouClient();
        try {
            String response = client.sendRequest(data);
            System.out.println("Response: " + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String companyTips(String companyName)  {
        UserMessage message = MessageUtil.generateWebSearchMessage();
        String data = JSONObject.from(message).toString();
        data = data.replace("\"#content#\"","[" +
//                "{\"type\":\"input_image\", \"image_url\":\""+imageURL+"\"}," +
                "{\"type\":\"input_text\", \"text\":\""+String.format(Prompt.CompanyPrompt,companyName)+"\"}" +
                "]");
        System.out.println(data);
        DouClient client = new DouClient();
        try {
            String response = client.sendRequest(data);
            System.out.println("Response: " + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }






//    public static void main(String[] args) throws Exception {
//        DouService douService = new DouService();
//       douService.companyTips("中联园微分金股份有限公司");
////        douService.handleMessage("成都有哪些美食","https://ark-common-storage-prod-cn-beijing.tos-cn-beijing.volces.com/experience/2121378782/0/20260304/100dbafc-f0d0-48cc-bfe1-2de6190ca500.jpg?X-Tos-Algorithm=TOS4-HMAC-SHA256&X-Tos-Credential=AKLTMjgxMzUwNzliYzdlNDE4MTllYjJjZGVlOWQ3N2M1ZDY%2F20260304%2Fcn-beijing%2Ftos%2Frequest&X-Tos-Date=20260304T034236Z&X-Tos-Expires=604800&X-Tos-Signature=ed4477153532d11ab9f5278b7309b97094441a6700833ce945c2774413931abf&X-Tos-SignedHeaders=host");
//    }





}

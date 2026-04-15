package mobile.tiantai.android.util;


import mobile.tiantai.android.bo.*;
import mobile.tiantai.android.bo.scMobile.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {
    private static String BEGIN = "序|条码";
    private static String BEGIN_B = "序号|条码";
    private static String END = "合计";
    private static String N = "\\n";
    public static UserMessage generateUserMessage(){
        UserMessage userMessage = new UserMessage();
        userMessage.setModel("doubao-seed-2-0-lite-260215");
        userMessage.setThinking(new ThinkingType("disabled"));
        Content content = new Content("user","#content#");
        List<Content> contentList = new ArrayList<>();
        contentList.add(content);
        userMessage.setInput(contentList);
        return userMessage;
    }
    public static UserMessage generateWebSearchMessage(){
        UserMessage userMessage = new UserMessage();
        userMessage.setModel("doubao-seed-2-0-lite-260215");
        userMessage.setThinking(new ThinkingType("disabled"));
        List<Tools> tools = new ArrayList<>();
        Tools tools1 = new Tools();
        tools1.setType("web_search");
        tools.add(tools1);
        userMessage.setTools(tools);
        Content content = new Content("user","#content#");
        List<Content> contentList = new ArrayList<>();
        contentList.add(content);
        userMessage.setInput(contentList);
        return userMessage;
    }

    public static List<String> generateRobotData(String result){
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

    public static boolean match(String text, String regex){
        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public static boolean find(String text, String regex){
        List<String> target = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            target.add(matcher.group());
            count++;
        }
        if(count > 0){
           return true;
        }
        return false;
    }
}

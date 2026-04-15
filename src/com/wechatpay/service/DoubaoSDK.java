package com.wechatpay.service;

import com.alibaba.fastjson.JSON;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.model.content.generation.CreateContentGenerationTaskRequest;
import com.volcengine.ark.runtime.model.content.generation.CreateContentGenerationTaskRequest.Content;
import com.volcengine.ark.runtime.model.content.generation.CreateContentGenerationTaskResult;
import com.volcengine.ark.runtime.model.content.generation.GetContentGenerationTaskRequest;
import com.volcengine.ark.runtime.model.content.generation.GetContentGenerationTaskResponse;
import com.volcengine.ark.runtime.model.images.generation.GenerateImagesRequest;
import com.volcengine.ark.runtime.model.images.generation.ImagesResponse;
import com.volcengine.ark.runtime.model.images.generation.ResponseFormat;
import com.volcengine.ark.runtime.model.responses.constant.ResponsesConstants;
import com.volcengine.ark.runtime.model.responses.content.*;
import com.volcengine.ark.runtime.model.responses.item.*;
import com.volcengine.ark.runtime.model.responses.request.CreateResponsesRequest;
import com.volcengine.ark.runtime.model.responses.request.ResponsesInput;
import com.volcengine.ark.runtime.model.responses.response.ResponseObject;
import com.volcengine.ark.runtime.model.responses.tool.ResponsesTool;
import com.volcengine.ark.runtime.model.responses.tool.ToolWebSearch;
import com.volcengine.ark.runtime.service.ArkService;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DoubaoSDK {
    private static String  apiKey = "";
	//e448ff60-7748-41e8-8d18-d60447a64596
	//"97894268-353a-4592-9242-b11df7944f4f"
    /**
     *
     * 文本生成
     传入文本类信息给模型，进行问答、分析、改写、摘要、编程、翻译等任务，并返回文本结果
     * @param text
     * @return
     */
    public static ResponseObject textMode(String text){
        //String apiKey = System.getenv("ARK_API_KEY");
        // The base URL for model invocation
        ArkService arkService = ArkService.builder().apiKey(apiKey).baseUrl("https://ark.cn-beijing.volces.com/api/v3").build();

        CreateResponsesRequest request = CreateResponsesRequest.builder()
                .model("doubao-seed-2-0-pro-260215")
                .input(ResponsesInput.builder().stringValue(text).build()) // Replace with your prompt
                // .thinking(ResponsesThinking.builder().type(ResponsesConstants.THINKING_TYPE_DISABLED).build()) //  Manually disable deep thinking
                .build();

        ResponseObject resp = arkService.createResponse(request);
        System.out.println(resp);

        arkService.shutdownExecutor();
        return resp;
    }

    /**
     *
     * 多模态理解
     传入图片、视频、PDF文件给模型，进行分析、内容审核、问答、视觉定位等基于多模态理解相关任务，并返回文本结果。
     * @return
     */
    public static ResponseObject multiMode(List<String> filePath,String formateFile,String text){
//        String imgurl1 = "https://ark-project.tos-cn-beijing.volces.com/doc_image/ark_demo_img_1.png";
//        String imgurl2 = "https://ark-project.tos-cn-beijing.volces.com/doc_image/ark_demo_img_1.png";


        ArkService arkService = ArkService.builder().apiKey(apiKey).baseUrl("https://ark.cn-beijing.volces.com/api/v3")
                .build();


        MessageContent.Builder builder =  MessageContent.builder();


        // 转 String 数组
        String[] strArray = JSON.parseObject(filePath.get(0), String[].class);



        if(formateFile.equals("image")){

                 for (String s : strArray) {
                     builder.addListItem(InputContentItemImage.builder()
                             .imageUrl(s).build());
                 }

         }else{
            for (String s : strArray) {
                builder.addListItem(InputContentItemFile.InputContentItemFileBuilder.anInputContentItemFile().fileUrl(s).build());

            }

         }




        builder.addListItem(InputContentItemText.builder().text(text).build()).build();
        MessageContent messageContent = builder.build();
        ResponsesInput input  =  ResponsesInput.builder().addListItem(
                ItemEasyMessage.builder().role(ResponsesConstants.MESSAGE_ROLE_USER).content(messageContent)
                        .build())
                .build();


        CreateResponsesRequest request = CreateResponsesRequest.builder()
                .model("doubao-seed-2-0-pro-260215")
                .input(input)
                .build();
        ResponseObject resp = arkService.createResponse(request);
        System.out.println(resp);

        arkService.shutdownExecutor();

        return resp;
    }

    /**
     * 图片生成
     传入图片、文字给模型，进行：广告、海报、组图等图片生成；增改元素、颜色更换等图片编辑；油墨、水墨等风格切换
     */
    public static String imageGenerations(String text, String imgurl, String size, List<String> imglist){
        if("".equals(text)){
            text="根据参考图生成图片";
        }
        System.out.println("imglist"+imglist);
        //imgurl = "https://ark-project.tos-cn-beijing.volces.com/doc_image/ark_demo_img_1.png";
        //imgurl="https://impf2010.com/upload_files/seal.jpg";

       // text = "充满活力的特写编辑肖像，模特眼神犀利，头戴雕塑感帽子，色彩拼接丰富，眼部焦点锐利，景深较浅，具有Vogue杂志封面的美学风格，采用中画幅拍摄，工作室灯光效果强烈。";
     //   text= "保留印章内容不变，将背景抠图成透明背景，分辨率：180x180px。PNG格式";
        size = "2K";
        ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
        Dispatcher dispatcher = new Dispatcher();
        ArkService service = ArkService.builder()
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3") // The base URL for model invocation
                .dispatcher(dispatcher)
                .connectionPool(connectionPool)
                .apiKey(apiKey)

                .build();

        GenerateImagesRequest generateRequest = GenerateImagesRequest.builder()
                .model("doubao-seedream-5-0-260128") // Replace with Model ID
                .prompt(text)
            //   .image(imgurl)
               .image(imglist)
                .size(size)
                .sequentialImageGeneration("disabled")
                .responseFormat(ResponseFormat.Url)
                .stream(false)
                .watermark(false)
                .build();
        ImagesResponse imagesResponse = service.generateImages(generateRequest);
        System.out.println(imagesResponse.getData().get(0).getUrl());

        service.shutdownExecutor();

        return imagesResponse.getData().get(0).getUrl();
    }

    /**
     *
     * 视频生成
     通过文本描述、图像素材，快速生成高质量、风格多样的视频内容。
     * @return
     */
    public static String videoGeneration(String text,List<String> flist){
        if("".equals(text)){
            text="根据参考图生成视频";
        }

        ArkService service = ArkService.builder()
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3") // The base URL for model invocation
                .apiKey(apiKey)
                .build();

        System.out.println("----- create request -----");
        List<CreateContentGenerationTaskRequest.Content> contents = new ArrayList<>();
// ======================================
        // ✅ 关键：循环添加所有参考图（多张）
        // ======================================
        String[] strArray = com.alibaba.fastjson2.JSON.parseObject(flist.get(0), String[].class);
        for (String imgUrl : strArray) {
            contents.add(Content.builder()
                    .type("image_url") // 图片必须用这个类型
                    .imageUrl(new CreateContentGenerationTaskRequest.ImageUrl(imgUrl))
                    .build());
        }


//        // ✅ 1. 先传图片（必须 type=image_url）
//        contents.add(Content.builder()
//                .type("image_url")
//                .imageUrl(new CreateContentGenerationTaskRequest.ImageUrl(imgurl))
//                .build());

        // ✅ 2. 再传文本提示词
        contents.add(Content.builder()
                .type("text")
                .text(text)
                .build());


        // Create a video generation task
        CreateContentGenerationTaskRequest createRequest = CreateContentGenerationTaskRequest.builder()
                .model("doubao-seedance-1-5-pro-251215") // Replace with Model ID
                .content(contents)
                .build();

        CreateContentGenerationTaskResult createResult = service.createContentGenerationTask(createRequest);
        System.out.println(createResult);

        // Get the details of the task
        String taskId = createResult.getId();
        GetContentGenerationTaskRequest getRequest = GetContentGenerationTaskRequest.builder()
                .taskId(taskId)
                .build();

        System.out.println("----- polling task status -----");
        
         String videourl = "";
        while (true) {
            try {
                GetContentGenerationTaskResponse getResponse = service.getContentGenerationTask(getRequest);
                String status = getResponse.getStatus();
                if ("succeeded".equalsIgnoreCase(status)) {
                    System.out.println("----- task succeeded -----");
                    System.out.println(getResponse);
                    videourl = getResponse.getContent().getVideoUrl();
                    service.shutdownExecutor();
                    break;
                } else if ("failed".equalsIgnoreCase(status)) {
                    System.out.println("----- task failed -----");
                    System.out.println("Error: " + getResponse.getStatus());
                    service.shutdownExecutor();
                    break;
                } else {
                    System.out.printf("Current status: %s, Retrying in 3 seconds...\n", status);
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                System.err.println("Polling interrupted");
                service.shutdownExecutor();
                break;
            }
        }

        return videourl;
    }

    public static ResponseObject toolMode(){

        ArkService arkService = ArkService.builder().apiKey(apiKey).baseUrl("https://ark.cn-beijing.volces.com/api/v3").build();
        CreateResponsesRequest req = CreateResponsesRequest.builder()
                .model("doubao-seed-2-0-pro-260215")
                .input(ResponsesInput.builder().addListItem(
                        ItemEasyMessage.builder().role(ResponsesConstants.MESSAGE_ROLE_USER).content(
                                MessageContent.builder()
                                        .addListItem(InputContentItemText.builder().text("What's the weather like in Beijing?").build())
                                        .build()
                        ).build()
                ).build())
                .tools(buildTools())
                .build();
        ResponseObject resp = arkService.createResponse(req);
        System.out.println(resp);

        arkService.shutdownExecutor();
        return resp;
    }
    public static List<ResponsesTool> buildTools() {
        ToolWebSearch t = ToolWebSearch.builder().build();
        System.out.println(Arrays.asList(t));
        return Arrays.asList(t);
    }


    /**
     * 提取核心回复文本（保留原有方法）
     */
    public static String extractCoreText(ResponseObject responseObject) {
        if (responseObject == null
                || !"completed".equals(responseObject.getStatus())
                || responseObject.getOutput() == null
                || responseObject.getOutput().isEmpty()) {
            return "";
        }


           String m1 = "";
        for (BaseItem outputItem : responseObject.getOutput()) {

            if ("message".equals(outputItem.getType())) {
                ItemOutputMessage message = (ItemOutputMessage) outputItem;
                List<OutputContentItem>  listout = message.getContent();

                for (int j = 0;j<listout.size();j++){
                    OutputContentItemText outputContentItem = (OutputContentItemText)listout.get(j);
                    m1 = outputContentItem.getText();
                    System.out.println(outputContentItem.getText());
                }

            }

        }
        return m1;
    }

    /**
     * 新增：提取推理过程文本
     */
    public static String extractReasoningText(ResponseObject responseObject) {
        // 基础校验
        if (responseObject == null
                || !"completed".equals(responseObject.getStatus())
                || responseObject.getOutput() == null
                || responseObject.getOutput().isEmpty()) {
            return "";
        }
        String r1 = "";
        // 遍历output数组，定位reasoning类型的项
        for (BaseItem outputItem : responseObject.getOutput()) {


            if ("reasoning".equals(outputItem.getType())) {
                ItemReasoning reasoning = (ItemReasoning) outputItem;
                List<ReasoningSummaryPart> relist = reasoning.getSummary();
                for (int j = 0;j<relist.size();j++){
                    ReasoningSummaryPart reasoningSummaryPart = relist.get(j);
                    r1 = reasoningSummaryPart.getText();
                    System.out.println(reasoningSummaryPart.getText());
                }
            }
        }
        return r1;
    }


    public static String chatCompletions(String text){
        // The base URL for model invocation
        ArkService service = ArkService.builder().apiKey(apiKey).baseUrl("https://ark.cn-beijing.volces.com/api/v3").build();
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(text).build();
        messages.add(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("doubao-seed-2-0-pro-260215")//Replace with Model ID
                .messages(messages)
                .stream(true)
                .thinking(new ChatCompletionRequest.ChatCompletionRequestThinking("disabled"))
                .build();
        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace) // 处理错误
                .blockingForEach(response -> {
                    if (response.getChoices() != null && !response.getChoices().isEmpty()) {
                        String content = String.valueOf(response.getChoices().get(0).getMessage().getContent());
                        if (content != null) {
                            System.out.print(content); // 注意用print而非println，保持内容连续
                        }
                    }
                });
        // shutdown service
        service.shutdownExecutor();

        return null;
    }
    public static void main(String[] args) {
        chatCompletions("常见的十字花科植物有哪些？");

    }

    public static boolean containsChinese(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        for (char c : text.toCharArray()) {
            // 中文 Unicode 范围
            if (c >= '\u4E00' && c <= '\u9FFF') {
                return true;
            }
        }
        return false;
    }


}

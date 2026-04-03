package hy.ea.collage.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.aliyuncs.vod.model.v20170321.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VODService {

    // 点播服务所在的Region，接入服务中心为上海，则填cn-shanghai
    String regionId = "cn-shanghai";
    // 点播服务所在的Region，接入服务中心为上海，则填cn-shanghai
    String sysEndpoint = "sts.aliyuncs.com";

    private DefaultAcsClient createClient() {
        // 从环境变量中获取步骤一生成的RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
        String accessKeyId = "LTAI5tFtgoeYRZnEUvYPjnQG";
        String accessKeySecret = "5ej1ctGjzhnIdw6kqfFV7drorVwOUR";
        //构造default profile（参数留空，无需添加Region ID）
            /*
            说明：当设置SysEndpoint为sts.aliyuncs.com时，regionId可填可不填；反之，regionId必填，根据使用的服务区域填写，例如：cn-shanghai
            详情参考STS各地域的Endpoint。
             */
        IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
        //用profile构造client
        return new DefaultAcsClient(profile);
    }

    private AssumeRoleResponse assumeRole(String roleArn, String roleSessionName, String policy) throws ClientException {
        try {
            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setSysEndpoint(sysEndpoint);
            request.setSysMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            // 发起请求，并得到response
            return createClient().getAcsResponse(request);
        } catch (ClientException e) {
            throw e;
        }
    }

    private AssumeRoleResponse createSecurityToken() throws ClientException {
        // AssumeRole API 请求参数：RoleArn, RoleSessionName, Policy, and DurationSeconds
        // RoleArn需要通过步骤三在RAM控制台上获取
        String roleArn = "acs:ram::1072975177243098:role/hyplat";
        // RoleSessionName 角色会话名称，自定义参数
        String roleSessionName = "session-name";
        // 定制你的policy
        String policy = "{\n" +
                "  \"Version\": \"1\",\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Action\": \"vod:*\",\n" +
                "      \"Resource\": \"*\",\n" +
                "      \"Effect\": \"Allow\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            AssumeRoleResponse response = assumeRole(roleArn, roleSessionName, policy);
//            System.out.println("Expiration: " + response.getCredentials().getExpiration());
//            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
//            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
//            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
//            System.out.println("RequestId: " + response.getRequestId());

            return response;
        } catch (ClientException e) {
//            System.out.println("Failed to get a token.");
//            System.out.println("Error code: " + e.getErrCode());
//            System.out.println("Error message: " + e.getErrMsg());
            throw e;
        }
    }

    public CreateUploadVideoResponse createUploadVideo(String title, String fileName, Long fileSize) throws ClientException {
        AssumeRoleResponse assumeRoleResponse = createSecurityToken();
        String accessKeyId = assumeRoleResponse.getCredentials().getAccessKeyId();
        String accessKeySecret = assumeRoleResponse.getCredentials().getAccessKeySecret();
        String securityToken = assumeRoleResponse.getCredentials().getSecurityToken();

        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setSecurityToken(securityToken);
        request.setTitle(title);
        request.setFileName(fileName);
        request.setFileSize(fileSize);
        request.setTemplateGroupId("8b0f0cf441a670404e16d852e9adf35a");

        try {
            CreateUploadVideoResponse response = client.getAcsResponse(request);
//            System.out.println("CreateUploadVideoRequest, " + request.getUrl());
//            System.out.println("CreateUploadVideoRequest, requestId:" + response.getRequestId());
//            System.out.println("UploadAddress, " + response.getUploadAddress());
//            System.out.println("UploadAuth, " + response.getUploadAuth());
//            System.out.println("VideoId, " + response.getVideoId());

            return response;
        } catch (ClientException e) {
//            System.out.println("action, error:" + e);
            throw e;
        }
    }

    public GetVideoInfosResponse getVideoInfos(String videoIds) throws ClientException {
        AssumeRoleResponse assumeRoleResponse = createSecurityToken();
        String accessKeyId = assumeRoleResponse.getCredentials().getAccessKeyId();
        String accessKeySecret = assumeRoleResponse.getCredentials().getAccessKeySecret();
        String securityToken = assumeRoleResponse.getCredentials().getSecurityToken();

        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        try {
            GetVideoInfosRequest request = new GetVideoInfosRequest();
            request.setSecurityToken(securityToken);
            request.setVideoIds(videoIds);

            GetVideoInfosResponse response = client.getAcsResponse(request);
            List<GetVideoInfosResponse.Video> videoList = response.getVideoList();
            List<String> nonExistVideoIds = response.getNonExistVideoIds();
//            System.out.println("GetVideoInfosRequest, " + request.getUrl());
//            System.out.println("GetVideoInfosRequest, requestId:" + response.getRequestId());
//            System.out.println("NonExistVideoIds:" + String.join(",", nonExistVideoIds));
//            System.out.println("VideoInfos: " + videoList.size());

            return response;
        } catch (ClientException e) {
//            System.out.println("Failed to get video infos.");
//            System.out.println("Error code: " + e.getErrCode());
//            System.out.println("Error message: " + e.getErrMsg());
            throw e;
        }
    }

    public GetPlayInfoResponse getPlayInfo(String videoId) throws ClientException {
        AssumeRoleResponse assumeRoleResponse = createSecurityToken();
        String accessKeyId = assumeRoleResponse.getCredentials().getAccessKeyId();
        String accessKeySecret = assumeRoleResponse.getCredentials().getAccessKeySecret();
        String securityToken = assumeRoleResponse.getCredentials().getSecurityToken();

        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        try {
            GetPlayInfoRequest request = new GetPlayInfoRequest();
            request.setVideoId(videoId);
            request.setFormats("m3u8");
            request.setSecurityToken(securityToken);

            GetPlayInfoResponse response = client.getAcsResponse(request);
            GetPlayInfoResponse.VideoBase videoBase = response.getVideoBase();
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
//            System.out.println("GetPlayInfoRequest, " + request.getUrl());
//            System.out.println("GetPlayInfoRequest, requestId:" + response.getRequestId());
//            System.out.println("CoverURL:" + videoBase.getCoverURL());
//            System.out.println("PlayURL: " + playInfoList.get(0).getPlayURL());

            return response;
        } catch (ClientException e) {
//            System.out.println("Failed to get play info.");
//            System.out.println("Error code: " + e.getErrCode());
//            System.out.println("Error message: " + e.getErrMsg());
            throw e;
        }
    }
}

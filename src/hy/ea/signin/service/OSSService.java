package hy.ea.signin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

@Service
public class OSSService {
	private static final Logger logger = LoggerFactory.getLogger(OSSService.class);

    // 点播服务所在的Region，接入服务中心为上海，则填cn-shanghai
    String regionId = "cn-shanghai";
    // 点播服务所在的Region，接入服务中心为上海，则填cn-shanghai
    String sysEndpoint = "sts.aliyuncs.com";

    private DefaultAcsClient createClient() {
        // 从环境变量中获取步骤一生成的RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
        String accessKeyId = "LTAIhoOYsMdxmhy1";
        String accessKeySecret = "DUXMnK26k9kzwqXu9A6oi2KV8UlMn2";
        //构造default profile（参数留空，无需添加Region ID）
            /*
            说明：当设置SysEndpoint为sts.aliyuncs.com时，regionId可填可不填；反之，regionId必填，根据使用的服务区域填写，例如：cn-shanghai
            详情参考STS各地域的Endpoint。
             */
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
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

    public AssumeRoleResponse createSecurityToken() throws ClientException {
        // AssumeRole API 请求参数：RoleArn, RoleSessionName, Policy, and DurationSeconds
        // RoleArn需要通过步骤三在RAM控制台上获取
        String roleArn = "acs:ram::1407636345046943:role/oss";
        // RoleSessionName 角色会话名称，自定义参数
        String roleSessionName = "session-name";
        // 定制你的policy
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:PutObject\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:kaoqinsh-hyplat\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        try {
            AssumeRoleResponse response = assumeRole(roleArn, roleSessionName, null);
            logger.info("调试信息");
            logger.info("调试信息");
            logger.info("调试信息");
            logger.info("调试信息");
            logger.info("RequestId: : {}", response.getRequestId());
            return response;
        } catch (ClientException e) {
            logger.info("Failed to get a token.");
            logger.info("Error code: : {}", e.getErrCode());
            logger.info("Error message: : {}", e.getErrMsg());
            throw e;
        }
    }

    public String uploadObject(String faceUrl, String objectName) {
        String bucketName = "kaoqinsh-hyplat";
        String endpoint = "https://oss-cn-shanghai.aliyuncs.com";

        try {
            AssumeRoleResponse assumeRoleResponse = createSecurityToken();
            String accessKeyId = assumeRoleResponse.getCredentials().getAccessKeyId();
            String accessKeySecret = assumeRoleResponse.getCredentials().getAccessKeySecret();
            String securityToken = assumeRoleResponse.getCredentials().getSecurityToken();

            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, securityToken);

            URL url = new URL(faceUrl);
            InputStream inputStream = url.openConnection().getInputStream();
            PutObjectRequest request = new PutObjectRequest(bucketName, objectName, inputStream);

            PutObjectResult response = ossClient.putObject(request);
            logger.info("CreateUploadVideoRequest, requestId:: {}", response.getRequestId());
            ossClient.shutdown();

            return "https://" + bucketName + ".oss-cn-shanghai.aliyuncs.com" + "/" + objectName;
        } catch (OSSException oe) {
            logger.info("调试信息");
            logger.info("Error Message:: {}", oe.getErrorMessage());
            logger.info("Error Code:: {}", oe.getErrorCode());
            logger.info("Request ID:: {}", oe.getRequestId());
            logger.info("Host ID:: {}", oe.getHostId());
        } catch (Exception e) {
            logger.info("Caught an exception: : {}", e);
        }
        return null;
    }
}

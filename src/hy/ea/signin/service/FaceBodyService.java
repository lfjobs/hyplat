package hy.ea.signin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.facebody.model.v20191230.*;
import com.aliyuncs.profile.DefaultProfile;
import hy.ea.interceptor.JsonHandlerException;
import org.springframework.stereotype.Service;

@Service
public class FaceBodyService {
	private static final Logger logger = LoggerFactory.getLogger(FaceBodyService.class);

    private IAcsClient getClient() {
        String accessKeyId = "LTAIhoOYsMdxmhy1";
        String accessKeySecret = "DUXMnK26k9kzwqXu9A6oi2KV8UlMn2";

        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKeyId, accessKeySecret);

        return new DefaultAcsClient(profile);
    }

    public AddFaceEntityResponse addFaceEntity(String entityId) throws JsonHandlerException {

        AddFaceEntityRequest request = new AddFaceEntityRequest();
        request.setDbName("default");
        request.setEntityId(entityId);
//        request.setLabels("");

        try {
            AddFaceEntityResponse response = getClient().getAcsResponse(request);
            logger.info("AddFaceRequest, : {}", request.getUrl());
            logger.info("AddFaceEntityResponse, requestId:: {}", response.getRequestId());

            return response;
        } catch (ClientException e) {
            logger.info("Failed to add face.");
            logger.info("Error code: : {}", e.getErrCode());
            logger.info("Error message: : {}", e.getErrMsg());
            throw new JsonHandlerException(e.getErrCode(), e.getErrMsg(), e);
        }
    }

    public AddFaceResponse addFace(String imageUrl, String entityId, String extraData) throws JsonHandlerException {

        AddFaceRequest request = new AddFaceRequest();
        request.setDbName("default");
        request.setImageUrl(imageUrl);
        request.setEntityId(entityId);
        request.setExtraData(extraData);
//        request.setQualityScoreThreshold(50);
//        request.setSimilarityScoreThresholdInEntity(.0f);
//        request.setSimilarityScoreThresholdBetweenEntity(.0f);

        try {
            AddFaceResponse response = getClient().getAcsResponse(request);
            logger.info("AddFaceRequest, : {}", request.getUrl());
            logger.info("getAcsResponse, requestId:: {}", response.getRequestId());
            logger.info("调试信息");
            logger.info("调试信息");

            return response;
        } catch (ClientException e) {
            logger.info("Failed to add face.");
            logger.info("Error code: : {}", e.getErrCode());
            logger.info("Error message: : {}", e.getErrMsg());
            throw new JsonHandlerException(e.getErrCode(), e.getErrMsg(), e);
        }
    }

    public SearchFaceResponse searchFace(String imageUrl) throws JsonHandlerException {

        try {
            SearchFaceRequest request = new SearchFaceRequest();
            request.setDbName("default");
            request.setImageUrl(imageUrl);
            request.setLimit(1);
//            request.setDbNames("");
//            request.setQualityScoreThreshold(50.f);
//            request.setMaxFaceNum(1L);

            SearchFaceResponse response = getClient().getAcsResponse(request);
            logger.info("SearchFaceRequest, : {}", request.getUrl());
            logger.info("SearchFaceResponse, requestId:: {}", response.getRequestId());
            logger.info("调试信息");

            return response;
        } catch (ClientException e) {
            logger.info("Failed to search face.");
            logger.info("Error code: : {}", e.getErrCode());
            logger.info("Error message: : {}", e.getErrMsg());
            throw new JsonHandlerException(e.getErrCode(), e.getErrMsg(), e);
        }
    }


    public RecognizeFaceResponse recognizeFace(String imageUrl) throws JsonHandlerException {

        try {
            RecognizeFaceRequest request = new RecognizeFaceRequest();
            request.setImageURL(imageUrl);
            request.setQuality(true);

            RecognizeFaceResponse response = getClient().getAcsResponse(request);
            logger.info("SearchFaceRequest, : {}", request.getUrl());
            logger.info("SearchFaceResponse, requestId:: {}", response.getRequestId());
            logger.info("调试信息");

            return response;
        } catch (ClientException e) {
            logger.info("Failed to search face.");
            logger.info("Error code: : {}", e.getErrCode());
            logger.info("Error message: : {}", e.getErrMsg());
            throw new JsonHandlerException(e.getErrCode(), e.getErrMsg(), e);
        }
    }
}

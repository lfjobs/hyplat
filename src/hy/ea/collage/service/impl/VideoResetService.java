package hy.ea.collage.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoInfosResponse;
import hy.ea.collage.action.dto.VODProvider;
import hy.ea.collage.action.dto.ViewVideoDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class VideoResetService {

    @Resource
    private VODService vodService;

    public <T extends ViewVideoDto> void resetVideoInfos(List<T> videos) throws ClientException {
        if (videos.isEmpty()) {
            return;
        }

        Map<String, ArrayList<String>> providerGroup = new HashMap<>();

        for (T video : videos) {
            String providerName = video.getVodProvider();

            if (StringUtils.isBlank(providerName)) {
                continue;
            }

            if (!providerGroup.containsKey(providerName)) {
                providerGroup.put(providerName, new ArrayList<>());
            }

            String vodId = video.getVodId();
            providerGroup.get(providerName).add(vodId);
        }

        for (String groupName : providerGroup.keySet()) {
            VODProvider provider = VODProvider.getByName(groupName);
            if (provider == null) {
                return;
            }
            switch (provider) {
                case ALIYUN:
                    resetAliyunVideoInfos(videos, providerGroup.get(groupName));
                    break;
                case HUAWEI:
                    break;
            }
        }
    }

    public <T extends ViewVideoDto> void resetAliyunVideoInfos(List<T> videos, List<String> videoIds) throws ClientException {
        if (videos.isEmpty() || videoIds.isEmpty()) {
            return;
        }

        GetVideoInfosResponse response = vodService.getVideoInfos(String.join(",", videoIds));

        List<String> nonExistVideoIds = response.getNonExistVideoIds();
        for (String nonExistVideoId : nonExistVideoIds) {
            if (videos.isEmpty()) {
                return;
            }

            List<T> removeVideos = new ArrayList<>();
            for (T video : videos) {
                if (nonExistVideoId.equals(video.getVodId())) {
                    removeVideos.add(video);
                }
            }
            videos.removeAll(removeVideos);
        }

        List<GetVideoInfosResponse.Video> existVideos = response.getVideoList();
        for (T video : videos) {
            for (GetVideoInfosResponse.Video existVideo : existVideos) {
                if (video.getVodId().equals(existVideo.getVideoId())) {
                    video.setCoverUrl(existVideo.getCoverURL());
                }
            }
        }
    }

    public <T extends ViewVideoDto> void resetVideoPlayInfos(List<T> videos) throws ClientException, InterruptedException {
        if (videos.isEmpty()) {
            return;
        }

        Map<String, ArrayList<T>> providerGroup = new HashMap<>();

        for (T video : videos) {
            String providerName = video.getVodProvider();

            if (StringUtils.isBlank(providerName)) {
                continue;
            }

            if (!providerGroup.containsKey(providerName)) {
                providerGroup.put(providerName, new ArrayList<>());
            }

            providerGroup.get(providerName).add(video);
        }

        for (String groupName : providerGroup.keySet()) {
            VODProvider provider = VODProvider.getByName(groupName);
            if (provider == null) {
                return;
            }
            switch (provider) {
                case ALIYUN:
                    ExecutorService executorService = Executors.newFixedThreadPool(5);
                    CountDownLatch countDownLatch = new CountDownLatch(providerGroup.get(groupName).size());
                    for (ViewVideoDto video : providerGroup.get(groupName)) {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    resetAliyunVideoPlayInfo(video);
                                } catch (ClientException e) {
                                    videos.remove(video);
                                    throw new RuntimeException(e);
                                } finally {
                                    countDownLatch.countDown();
                                }
                            }
                        });
                    }
                    countDownLatch.await();
                    break;
                case HUAWEI:
                    break;
            }
        }
    }

    public <T extends ViewVideoDto> void resetVideoPlayInfo(T video) throws ClientException {
        if (StringUtils.isNotBlank(video.getVodId()) && StringUtils.isNotBlank(video.getVodProvider())) {
            String providerName = video.getVodProvider();
            String vodId = video.getVodId();

            VODProvider provider = VODProvider.getByName(providerName);
            if (provider == null) {
                return;
            }
            switch (provider) {
                case ALIYUN:
                    resetAliyunVideoPlayInfo(video);
                    break;
                case HUAWEI:
                    break;
            }
        }
    }

    public <T extends ViewVideoDto> void resetAliyunVideoPlayInfo(T video) throws ClientException {
        GetPlayInfoResponse response = vodService.getPlayInfo(video.getVodId());

        GetPlayInfoResponse.VideoBase videoBase = response.getVideoBase();
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        if (playInfoList.isEmpty()) {
            return;
        }

        GetPlayInfoResponse.PlayInfo hls = playInfoList.get(0);
        video.setPlayUrl(hls.getPlayURL());
        video.setCoverUrl(videoBase.getCoverURL());
    }
}

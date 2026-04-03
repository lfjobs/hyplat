package hy.ea.collage.action.dto;

import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewVideoDto implements Serializable {
    private String videoId;     // 视频ID权限
    private String vodId;       // 视频点播服务videoID
    private String vodProvider;       // 视频点播服务videoID
    private String playUrl;    // 视频地址
    private String coverUrl;    // 视频明面

    private String title;
    private String authorId;// 作者ID
    private String authorName;// 作者名称
    private String avatar;// 头像
    private String createdDate;// 权限
    private String likedCount;// 视频被赞数
    private String commentCount;// 视频评论数
    private String sharedCount;//分享视频数
    private String isLiked;//是否点赞 0未赞 1已赞
    private String isFollowed;// 粉丝 有可能为空，没有注册
    private String productName; //产品主题图片
    private String productImage;// 物品名称
    private String priceType; //价格类型 1零售 2批发

    public ViewVideoDto() {
    }

    public ViewVideoDto(String videoId, String vodId, String vodProvider, String playUrl, String coverUrl, String title, String authorId, String authorName, String avatar, String createdDate, String likedCount, String commentCount, String sharedCount, String isLiked, String isFollowed, String productName, String productImage, String priceType) {
        this.videoId = videoId;
        this.vodId = vodId;
        this.vodProvider = vodProvider;
        this.playUrl = playUrl;
        this.coverUrl = coverUrl;
        this.title = title;
        this.authorId = authorId;
        this.authorName = authorName;
        this.avatar = avatar;
        this.createdDate = createdDate;
        this.likedCount = likedCount;
        this.commentCount = commentCount;
        this.sharedCount = sharedCount;
        this.isLiked = isLiked;
        this.isFollowed = isFollowed;
        this.productName = productName;
        this.productImage = productImage;
        this.priceType = priceType;
    }

    protected static String isNull(Object tep) {
        if (tep == null || "null".equals(tep)) {
            return "";
        } else {
            return String.valueOf(tep);
        }
    }

    public static Pageable<ViewVideoDto> fromPageForm(PageForm pageForm) {
        if (pageForm == null || pageForm.getList().isEmpty()) {
            return PageInfo.empty();
        }

        List<ViewVideoDto> myVideos = new ArrayList<>(Collections.emptyList());

        myVideos.addAll(fromRows(pageForm.getList()));

        Pageable<ViewVideoDto> pageInfo = new PageInfo<>();
        pageInfo.setPageNumber(pageForm.getPageNumber());
        pageInfo.setPageSize(pageForm.getPageSize());
        pageInfo.setTotalElements(pageForm.getRecordCount());
        pageInfo.setContent(myVideos);

        return pageInfo;
    }

    public static List<ViewVideoDto> fromRows(List<BaseBean> rows) {
        List<ViewVideoDto> myVideos = new ArrayList<>();

        for (Object row : rows) {
            Object[] cols = (Object[]) row;
            ViewVideoDto myVideo = fromCols(cols);
            myVideos.add(myVideo);
        }

        return myVideos;
    }

    public static ViewVideoDto fromCols(Object[] cols) {
        ViewVideoDto videoView = new ViewVideoDto();
        videoView.setVideoId(isNull(cols[0]));// 视频ID
        videoView.setVodId(isNull(cols[1]));// 视频点播服务videoID
        videoView.setVodProvider(isNull(cols[2]));// 视频点播服务提供商
        videoView.setPlayUrl(isNull(cols[3]));// 视频地址
        videoView.setTitle(isNull(cols[4]));// 标题
        videoView.setAuthorId(isNull(cols[5]));// 作者ID
        videoView.setAuthorName(isNull(cols[6]));// 作者名称
        videoView.setAvatar(isNull(cols[7]));// 头像
        videoView.setCreatedDate(isNull(cols[8]));// 创建时间
        videoView.setLikedCount(isNull(cols[9]));// 视频被赞数
        videoView.setCommentCount(isNull(cols[10]));// 视频评论数
        videoView.setSharedCount(isNull(cols[11]));//分享视频数
        videoView.setIsLiked(isNull(cols[12]));//是否点赞 0未赞 1已赞
        videoView.setIsFollowed(isNull(cols[13]));// 关注
        videoView.setCoverUrl(isNull(cols[14]));// 视频明面
//        videoView.setProductId(isNull(cols[14]));//ppid
        videoView.setProductImage(isNull(cols[16])); //产品主题图片
        videoView.setProductName(isNull(cols[17])); // 物品名称
//        videoView.setPriceType(isNull(cols[18])); //价格类型 1零售 2批发
        return videoView;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVodId() {
        return vodId;
    }

    public void setVodId(String vodId) {
        this.vodId = vodId;
    }

    public String getVodProvider() {
        return vodProvider;
    }

    public void setVodProvider(String vodProvider) {
        this.vodProvider = vodProvider;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(String likedCount) {
        this.likedCount = likedCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getSharedCount() {
        return sharedCount;
    }

    public void setSharedCount(String sharedCount) {
        this.sharedCount = sharedCount;
    }

    public String getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(String isLiked) {
        this.isLiked = isLiked;
    }

    public String getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(String isFollowed) {
        this.isFollowed = isFollowed;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }
}

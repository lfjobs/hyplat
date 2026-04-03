package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

public class GoodsBillsExtOpinion implements BaseBean, java.io.Serializable {
    private String id;
    private String cashierBillsID; //预算单id
    private String goodsBillsID; //预算单明细id
    private String reviewerId;//审核人Id
    private String reviewerName;//审核人名
    private String reviewerNameCode;//审核人编号
    private String reviewTime;//审核时间
    private String reviewOpinion;//审核意见   1:同意    0:驳回
    private String reviewNameSource;//审核人来源;

    public GoodsBillsExtOpinion() {
    }

    public GoodsBillsExtOpinion(String id, String cashierBillsID, String goodsBillsID, String reviewerId, String reviewerName, String reviewerNameCode, String reviewTime, String reviewOpinion, String reviewNameSource) {
        this.id = id;
        this.cashierBillsID = cashierBillsID;
        this.goodsBillsID = goodsBillsID;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.reviewerNameCode = reviewerNameCode;
        this.reviewTime = reviewTime;
        this.reviewOpinion = reviewOpinion;
        this.reviewNameSource = reviewNameSource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCashierBillsID() {
        return cashierBillsID;
    }

    public void setCashierBillsID(String cashierBillsID) {
        this.cashierBillsID = cashierBillsID;
    }

    public String getGoodsBillsID() {
        return goodsBillsID;
    }

    public void setGoodsBillsID(String goodsBillsID) {
        this.goodsBillsID = goodsBillsID;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewerNameCode() {
        return reviewerNameCode;
    }

    public void setReviewerNameCode(String reviewerNameCode) {
        this.reviewerNameCode = reviewerNameCode;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewOpinion() {
        return reviewOpinion;
    }

    public void setReviewOpinion(String reviewOpinion) {
        this.reviewOpinion = reviewOpinion;
    }

    public String getReviewNameSource() {
        return reviewNameSource;
    }

    public void setReviewNameSource(String reviewNameSource) {
        this.reviewNameSource = reviewNameSource;
    }
}

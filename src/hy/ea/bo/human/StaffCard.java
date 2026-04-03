package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

/**
 * 人员身份证
 *
 */
@SuppressWarnings("serial")
public class StaffCard implements BaseBean{
    private String cardKey;
    private String staffID;
    private String cardFrontUrl;
    private String cardReverseUrl;

    public String getCardKey() {
        return cardKey;
    }

    public void setCardKey(String cardKey) {
        this.cardKey = cardKey;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getCardFrontUrl() {
        return cardFrontUrl;
    }

    public void setCardFrontUrl(String cardFrontUrl) {
        this.cardFrontUrl = cardFrontUrl;
    }

    public String getCardReverseUrl() {
        return cardReverseUrl;
    }

    public void setCardReverseUrl(String cardReverseUrl) {
        this.cardReverseUrl = cardReverseUrl;
    }

    @Override
    public String toString() {
        return "StaffCard{" +
                "cardKey='" + cardKey + '\'' +
                ", staffID='" + staffID + '\'' +
                ", cardFrontUrl='" + cardFrontUrl + '\'' +
                ", cardReverseUrl='" + cardReverseUrl + '\'' +
                '}';
    }
}

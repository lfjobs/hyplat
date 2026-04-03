package com.faceSDK.faceVO;

public class ContentBodyVO {
    private String personId;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "ContentBodyVO{" +
                "personId='" + personId + '\'' +
                '}';
    }
}

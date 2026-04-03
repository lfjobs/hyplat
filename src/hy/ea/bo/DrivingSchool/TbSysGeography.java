package hy.ea.bo.DrivingSchool;

import hy.plat.bo.BaseBean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 行政区域信息表
 * Created by Administrator on 2017/8/17 0017.
 */

public class TbSysGeography  implements BaseBean,Serializable {
    private String geKey;
    private String geoBh;  //区域编号
    private String geoName;  //区域名称
    private String geoLevel;  //区域级别
    private String parentBh;  //上级编号
    private Long latitude; //经度
    private Long longitude;  //纬度
    private Long persons;  //人口数
    private Long floorarea;  //占地面积
    private String description;  //描述

    public TbSysGeography() {
    }

    public TbSysGeography(String geKey, String geoBh, String geoName, String geoLevel, String parentBh, Long latitude, Long longitude, Long persons, Long floorarea, String description) {
        this.geKey = geKey;
        this.geoBh = geoBh;
        this.geoName = geoName;
        this.geoLevel = geoLevel;
        this.parentBh = parentBh;
        this.latitude = latitude;
        this.longitude = longitude;
        this.persons = persons;
        this.floorarea = floorarea;
        this.description = description;
    }

    public String getGeKey() {
        return geKey;
    }

    public void setGeKey(String geKey) {
        this.geKey = geKey;
    }

    public String getGeoBh() {
        return geoBh;
    }

    public void setGeoBh(String geoBh) {
        this.geoBh = geoBh;
    }

    public String getGeoName() {
        return geoName;
    }

    public void setGeoName(String geoName) {
        this.geoName = geoName;
    }

    public String getGeoLevel() {
        return geoLevel;
    }

    public void setGeoLevel(String geoLevel) {
        this.geoLevel = geoLevel;
    }

    public String getParentBh() {
        return parentBh;
    }

    public void setParentBh(String parentBh) {
        this.parentBh = parentBh;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getPersons() {
        return persons;
    }

    public void setPersons(Long persons) {
        this.persons = persons;
    }

    public Long getFloorarea() {
        return floorarea;
    }

    public void setFloorarea(Long floorarea) {
        this.floorarea = floorarea;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

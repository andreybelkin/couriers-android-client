package com.globalgrupp.couriers.app.classes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by п on 01.02.2016.
 */
public class TaskResult implements Serializable{

    private Long id;

    private Long taskAddressResultLinkId;

    private Long result;

    private String comment;

    private String porch;

    private List<Long> photoIds;

    private List<String> photoPathList;


    private String addressString;


    private SimpleGeoCoords courierCoords;

    public SimpleGeoCoords getCourierCoords() {
        return courierCoords;
    }

    public void setCourierCoords(SimpleGeoCoords courierCoords) {
        this.courierCoords = courierCoords;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public List<String> getPhotoPathList() {
        return photoPathList;
    }

    public void setPhotoPathList(List<String> photoPathList) {
        this.photoPathList = photoPathList;
    }

    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Long> getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(List<Long> photoIds) {
        this.photoIds = photoIds;
    }

    public TaskResult() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPorch() {
        return porch;
    }

    public void setPorch(String porch) {
        this.porch = porch;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public Long getTaskAddressResultLinkId() {
        return taskAddressResultLinkId;
    }

    public void setTaskAddressResultLinkId(Long taskAddressResultLinkId) {
        this.taskAddressResultLinkId = taskAddressResultLinkId;
    }

    private Boolean correctPlace;

    public Boolean getCorrectPlace() {
        return correctPlace;
    }

    public void setCorrectPlace(Boolean correctPlace) {
        this.correctPlace = correctPlace;
    }
}

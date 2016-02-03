package com.globalgrupp.couriers.app.classes;

/**
 * Created by п on 01.02.2016.
 */
public class Address {

    private Long id;

    private String kv;

    private String street;

    private String houseNumber;

    private Long apartmentCount;

    private String postboxType;

    private String postboxQuality;

    private String houseQuality;

    private String levelCount;

    private Long porchCount;

    private String cityRayon;

    private String rayon;

    private String houseYear;

    private String key;

    private String comment;

    private String lastUpdate;

    private String Novostroyka;

    private Long taskAddresResultLinkId;

    public Long getTaskAddresResultLinkId() {
        return taskAddresResultLinkId;
    }

    public void setTaskAddresResultLinkId(Long taskAddresResultLinkId) {
        this.taskAddresResultLinkId = taskAddresResultLinkId;
    }

    public Address() {
    }

    public Long getApartmentCount() {
        return apartmentCount;
    }

    public void setApartmentCount(Long apartmentCount) {
        this.apartmentCount = apartmentCount;
    }

    public String getCityRayon() {
        return cityRayon;
    }

    public void setCityRayon(String cityRayon) {
        this.cityRayon = cityRayon;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseQuality() {
        return houseQuality;
    }

    public void setHouseQuality(String houseQuality) {
        this.houseQuality = houseQuality;
    }

    public String getHouseYear() {
        return houseYear;
    }

    public void setHouseYear(String houseYear) {
        this.houseYear = houseYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKv() {
        return kv;
    }

    public void setKv(String kv) {
        this.kv = kv;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(String levelCount) {
        this.levelCount = levelCount;
    }

    public String getNovostroyka() {
        return Novostroyka;
    }

    public void setNovostroyka(String novostroyka) {
        Novostroyka = novostroyka;
    }

    public Long getPorchCount() {
        return porchCount;
    }

    public void setPorchCount(Long porchCount) {
        this.porchCount = porchCount;
    }

    public String getPostboxQuality() {
        return postboxQuality;
    }

    public void setPostboxQuality(String postboxQuality) {
        this.postboxQuality = postboxQuality;
    }

    public String getPostboxType() {
        return postboxType;
    }

    public void setPostboxType(String postboxType) {
        this.postboxType = postboxType;
    }

    public String getRayon() {
        return rayon;
    }

    public void setRayon(String rayon) {
        this.rayon = rayon;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}

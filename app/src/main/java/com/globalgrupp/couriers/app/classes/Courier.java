package com.globalgrupp.couriers.app.classes;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lenovo on 27.01.2016.
 */

public class Courier {

    private Long id;

    private String name;

    private String appPushId;

    private Set<Task> tasks=new HashSet<Task>(0);

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Courier() {
    }

    public String getAppPushId() {
        return appPushId;
    }

    public void setAppPushId(String appPushId) {
        this.appPushId = appPushId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if (getName()!=null)
            return "Курьер "+getId().toString()+"("+getName()+")";
        else{
            return "Курьер "+getId().toString();
        }
    }
}

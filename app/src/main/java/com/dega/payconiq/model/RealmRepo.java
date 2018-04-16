package com.dega.payconiq.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by davedega on 12/04/18.
 */

public class RealmRepo extends RealmObject {

    @PrimaryKey
    private String id;

    private String name;
    private String description;
    private Integer watchersCount;
    private Integer stargazers;
    private Integer forks;

    public RealmRepo() {
    }

    public String getId() {
        return id;
    }

    public String getCountString() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getWatchersCount() {
        return watchersCount;
    }

    public Integer getStargazers() {
        return stargazers;
    }

    public Integer getForks() {
        return forks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWatchersCount(Integer watchersCount) {
        this.watchersCount = watchersCount;
    }

    public void setStargazers(Integer stargazers) {
        this.stargazers = stargazers;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }
}
package com.pjkurs.domain;

public class TrainingFile extends DBObject {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer id;
    public Integer training_id;
    public String path;
    public String description;
    public String name;
    public Boolean visibleForUsers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTraining_id() {
        return training_id;
    }

    public Boolean getVisibleForUsers() {
        return visibleForUsers;
    }

    public void setVisibleForUsers(Boolean visibleForUsers) {
        this.visibleForUsers = visibleForUsers;
    }

    public void setTraining_id(Integer training_id) {
        this.training_id = training_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

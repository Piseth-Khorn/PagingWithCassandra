package com.example.ManagementSystem.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Table
public class Role {
    @PrimaryKey
    private UUID id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate = new Date();
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;

    public Role() {
    }

    public Role(UUID id, String name, Date createDate, Date updateDate) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

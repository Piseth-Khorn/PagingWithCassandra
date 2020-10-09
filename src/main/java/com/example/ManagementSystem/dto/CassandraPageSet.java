package com.example.ManagementSystem.dto;

public class CassandraPageSet {
    private int pageIndex;
    private int limit;
    private String uuid;

    public CassandraPageSet() {
    }

    public CassandraPageSet(int pageIndex, int limit, String uuid) {
        this.pageIndex = pageIndex;
        this.limit = limit;
        this.uuid = uuid;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "CassandraPageSet{" +
                "pageIndex=" + pageIndex +
                ", limit=" + limit +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}

package hy.ea.collage.action.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageInfo<T extends Serializable> implements Pageable<T> {
    private Integer pageSize = 20;
    private Integer pageNumber = 1;
    private Integer totalElements = 0;
    private List<T> content = new ArrayList<>();

    public PageInfo() {
    }

    public PageInfo(Integer pageSize, Integer pageNumber, Integer totalElements, List<T> content) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.content = content;
    }

    static <T extends Serializable> PageInfo<T> empty() {
        return new PageInfo<>(20, 0, 0, new ArrayList<>());
    }

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    @Override
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public List<T> getContent() {
        return this.content;
    }

    @Override
    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public int getTotalPages() {
        return this.totalElements / this.pageSize + (this.totalElements % this.pageSize == 0 ? 0 : 1);
    }

    @Override
    public int getTotalElements() {
        return this.totalElements;
    }

    @Override
    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}

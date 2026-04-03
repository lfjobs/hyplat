package hy.ea.collage.action.dto;

import java.io.Serializable;
import java.util.List;

public interface Pageable<T extends Serializable> {

    public int getPageNumber();

    public void setPageNumber(int pageNumber);

    public int getPageSize();

    public void setPageSize(int pageSize);

    public List<T> getContent();

    public void setContent(List<T> content);

    public int getTotalPages();

    public int getTotalElements();

    public void setTotalElements(int totalElements);

}

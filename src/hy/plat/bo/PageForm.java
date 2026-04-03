/**
 * Page Form
 */
package hy.plat.bo;


import java.io.Serializable;
import java.util.List;

public class PageForm {
	private int pageSize = 20;
	private int pageNumber = 0;
	private int pageCount = 0;
	private int recordCount = 0;
	private List<BaseBean> list;
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public List<BaseBean> getList() {
		return list;
	}
	public void setList(List<BaseBean> list) {
		this.list = list;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
}



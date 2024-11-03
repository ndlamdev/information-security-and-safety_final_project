
package model.dto;

import java.util.List;

public class Page<T> {
    private long total;
    private int page;
    private int size;
    private long totalPage;
    private List<T> data;

    public Page(long total, int page, int size, List<T> data) {
        this.total = total;
        this.page = page;
        this.size = size;
        this.data = data;
        this.totalPage = (long)((int)Math.ceil((double)total / (double)size));
    }

    public String toString() {
        long var10000 = this.total;
        return "Page{total=" + var10000 + ", page=" + this.page + ", size=" + this.size + ", totalPage=" + this.totalPage + ", data=" + String.valueOf(this.data) + "}";
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = (long)total;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

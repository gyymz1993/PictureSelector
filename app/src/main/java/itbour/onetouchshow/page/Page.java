package itbour.onetouchshow.page;

public abstract class Page extends IPage {
    @Override
    public int handlePageIndex(int currPageIndex, int pageSize) {
        return ++currPageIndex;
    }

    @Override
    protected int handlePage(int currPageIndex, int pageSize) {
        return pageSize;
    }
}

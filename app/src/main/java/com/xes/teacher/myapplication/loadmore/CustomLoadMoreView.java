package com.xes.teacher.myapplication.loadmore;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.xes.teacher.myapplication.R;

public class CustomLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.tv_loading;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.tv_error;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.tv_finish;
    }
}

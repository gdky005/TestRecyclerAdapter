package com.xes.teacher.myapplication;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xes.teacher.myapplication.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;

// 参见：https://www.jianshu.com/p/b343fcff51b0
public class FirstFragment extends Fragment {

    private static final String TAG = "FirstFragment";

    private FragmentFirstBinding binding;
    private HomeAdapter mQuickAdapter;

    public static final int MAX_DATA = 40;


    private void initAdapter() {
        setAnimation();
        setOnClickListener();
        setHeaderAndFooter();


        mQuickAdapter.setOnLoadMoreListener(() -> {
            Log.d(TAG, "initAdapter: ");
            toast("开始刷新啦");
            getRecyclerView().postDelayed(() -> {
                //加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
//                homeAdapter.loadMoreComplete();
                //加载失败
//                homeAdapter.loadMoreFail();
                //加载结束
                mQuickAdapter.loadMoreEnd();
//                homeAdapter.loadMoreEnd(false);
            }, 1000);
        }, getRecyclerView());

        mQuickAdapter.setNewData(getData());
        // 禁用加载更多
        mQuickAdapter.disableLoadMoreIfNotFullPage();

        mQuickAdapter.setEnableLoadMore(false);

    }

    private void setHeaderAndFooter() {
        mQuickAdapter.addHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.item_header, null));
////        homeAdapter.addHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.item_header2, null));
//        homeAdapter.addFooterView(LayoutInflater.from(getContext()).inflate(R.layout.item_footer, null));

        // 这三个有点困惑。
//        homeAdapter.setHeaderFooterEmpty(false, false);
//        homeAdapter.setFooterViewAsFlow(true);
//        homeAdapter.setHeaderViewAsFlow(false);
    }

    private void setAnimation() {
        // 打开动画效果
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        // 添加自定义动画。
        mQuickAdapter.openLoadAnimation(view -> new Animator[] {
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.5f, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.5f, 1)
        });
        // 默认只有一次动画，设置为 false 会显示多次动画。
        mQuickAdapter.isFirstOnly(false);
    }

    private void setOnClickListener() {
        mQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
            TestBean testBean = (TestBean) adapter.getItem(position);
            if (testBean != null) {
                toast("click：" + testBean.getText());
            }
        });

        mQuickAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            TestBean testBean = (TestBean) adapter.getItem(position);
            if (testBean != null) {
                toast("long：" + testBean.getText());
            }
            return true;
        });

        mQuickAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TestBean testBean = (TestBean) adapter.getItem(position);

            switch (view.getId()) {
                case R.id.text1:
                    toast("click text1:" + position);
                    break;
                case R.id.text2:
                    toast("click text2:" + position);
                    break;
            }
        });

        mQuickAdapter.setOnItemChildLongClickListener((adapter, view, position) -> {
            TestBean testBean = (TestBean) adapter.getItem(position);

            switch (view.getId()) {
                case R.id.text1:
                    toast("long text1:" + position);
                    break;
                case R.id.text2:
                    toast("long text2:" + position);
                    break;
            }
            return true;
        });
    }

    /**
     * 显示 Toast
     * @param msg msg
     */
    private void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        ArrayList<TestBean> arrayList = new ArrayList<>();
//        getData(arrayList);

        mQuickAdapter = new HomeAdapter(arrayList);
        initAdapter();
        RecyclerView rvRecyclerView = getRecyclerView();
        rvRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRecyclerView.setAdapter(mQuickAdapter);
    }

    private ArrayList<TestBean> getData() {
        ArrayList<TestBean> arrayList = new ArrayList<>();
        for (int i = 0; i < MAX_DATA; i++) {
            TestBean testBean = new TestBean();
            testBean.setText("第" + (i + 1) + "个");
            arrayList.add(testBean);
        }
        return arrayList;
    }

    @NonNull
    private RecyclerView getRecyclerView() {
        return binding.rvRecyclerView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public class HomeAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
        public HomeAdapter(List data) {
            super(R.layout.item_home, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TestBean item) {
            helper.setText(R.id.text, item.getText());
            helper.setText(R.id.text1, helper.getAdapterPosition() + "_type1");
            helper.setText(R.id.text2, helper.getAdapterPosition() + "_type2");

            helper.addOnClickListener(R.id.text1);
            helper.addOnClickListener(R.id.text2);

            helper.addOnLongClickListener(R.id.text1);
            helper.addOnLongClickListener(R.id.text2);
        }
    }
}
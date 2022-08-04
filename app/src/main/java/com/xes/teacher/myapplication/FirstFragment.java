package com.xes.teacher.myapplication;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
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

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private HomeAdapter homeAdapter;


    private void initAdapter() {
        // 打开动画效果
        homeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        // 添加自定义动画。
        homeAdapter.openLoadAnimation(view -> new Animator[] {
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.5f, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.5f, 1)
        });
        // 默认只有一次动画，设置为 false 会显示多次动画。
        homeAdapter.isFirstOnly(false);

        homeAdapter.setOnItemClickListener((adapter, view, position) -> {
            TestBean testBean = (TestBean) adapter.getItem(position);
            if (testBean != null) {
                Toast.makeText(getContext(), "click：" + testBean.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        homeAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            TestBean testBean = (TestBean) adapter.getItem(position);
            if (testBean != null) {
                Toast.makeText(getContext(), "long：" + testBean.getText(), Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        homeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TestBean testBean = (TestBean) adapter.getItem(position);

            switch (view.getId()) {
                case R.id.text1:
                    Toast.makeText(getContext(), "click text1:" + position, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.text2:
                    Toast.makeText(getContext(), "click text2:" + position, Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        homeAdapter.setOnItemChildLongClickListener((adapter, view, position) -> {
            TestBean testBean = (TestBean) adapter.getItem(position);

            switch (view.getId()) {
                case R.id.text1:
                    Toast.makeText(getContext(), "long text1:" + position, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.text2:
                    Toast.makeText(getContext(), "long text2:" + position, Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
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
        for (int i = 0; i < 50; i++) {
            TestBean testBean = new TestBean();
            testBean.setText("第" + (i + 1) + "个");
            arrayList.add(testBean);
        }

        homeAdapter = new HomeAdapter(arrayList);
        initAdapter();
        RecyclerView rvRecyclerView = binding.rvRecyclerView;
        rvRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRecyclerView.setAdapter(homeAdapter);
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
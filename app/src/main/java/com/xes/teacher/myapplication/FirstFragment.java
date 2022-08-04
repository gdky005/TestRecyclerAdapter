package com.xes.teacher.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

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

        HomeAdapter homeAdapter = new HomeAdapter(R.layout.item_home, arrayList);
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
        public HomeAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TestBean item) {
            helper.setText(R.id.text, item.getText());
        }
    }
}
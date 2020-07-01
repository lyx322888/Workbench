package com.zpz.home.adapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zpz.common.base.MyARouter;
import com.zpz.common.base.adapter.SimpleBaseBindingAdapter;
import com.zpz.home.R;
import com.zpz.home.baen.FileListBean;
import com.zpz.home.databinding.ItemFileListBinding;

public class FileListAdapter extends SimpleBaseBindingAdapter<FileListBean.DataBean, ItemFileListBinding> {

    public FileListAdapter(Context context) {
        super(context, R.layout.item_file_list);
    }

    @Override
    protected void onSimpleBindItem(ItemFileListBinding binding, final FileListBean.DataBean item, RecyclerView.ViewHolder holder) {
        binding.setVm(item);
        binding.btnBj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyARouter.CreateProcedureActivity)
                        .withLong("company_id",item.getCompany_id())
                        .withLong("first_assess_id",0)
                        .navigation();
            }
        });
    }
}

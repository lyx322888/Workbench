package com.zpz.home.adapter;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zpz.common.base.adapter.SimpleBaseBindingAdapter;
import com.zpz.common.utils.GlideUtils;
import com.zpz.home.R;
import com.zpz.home.databinding.ItemHyPhotoBinding;

public class HyPhotoAdapter extends SimpleBaseBindingAdapter<String, ItemHyPhotoBinding> {

    public HyPhotoAdapter() {
        super( R.layout.item_hy_photo);
    }

    @Override
    protected void onSimpleBindItem(ItemHyPhotoBinding binding, final String item, final RecyclerView.ViewHolder holder) {
        binding.setStr(item);
        if (holder.getLayoutPosition()+1>mList.size()){
            binding.ivGb.setVisibility(View.GONE);
            binding.ivPhoto.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.bg_schytp));
        }else {
            binding.ivGb.setVisibility(View.VISIBLE);
            GlideUtils.showSmallPic(binding.ivPhoto,item);
        }
        binding.ivGb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList().remove(holder.getLayoutPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemHyPhotoBinding binding = DataBindingUtil.getBinding(holder.itemView);
            this.onBindItem(binding,  (position+1>mList.size())?"":mList.get(position) , holder);

        if (binding != null) {
            binding.executePendingBindings();
        }
        holder.itemView.setOnClickListener(v -> {
            if (mListener!=null){
                mListener.onItemClickListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return null == this.mList ? 1 : this.mList.size()+1;
    }
}

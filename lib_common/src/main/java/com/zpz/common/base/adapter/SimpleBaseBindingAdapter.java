/*
 * Copyright 2018-2020 KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zpz.common.base.adapter;

import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class SimpleBaseBindingAdapter<M, B extends ViewDataBinding> extends BaseBindingAdapter {

    private final int layout;

    public SimpleBaseBindingAdapter(int layout) {
        this.layout = layout;
    }

    @Override
    protected @LayoutRes
    int getLayoutResId(int viewType) {
        return this.layout;
    }

    protected abstract void onSimpleBindItem(B binding, M item, RecyclerView.ViewHolder holder);

    @Override
    protected void onBindItem(ViewDataBinding binding, Object item, RecyclerView.ViewHolder holder) {
        //noinspection unchecked
        onSimpleBindItem((B) binding, (M) item, holder);
    }
}
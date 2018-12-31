package com.ulling.firebasetest.view.adapter;

import com.ulling.firebasetest.R;
import com.ulling.firebasetest.databinding.RowInstagramBinding;
import com.ulling.firebasetest.entites.youtube.YoutubeItem;
import com.ulling.lib.core.ui.QcBaseLifeActivity;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewutil.adapter.QcBaseViewHolder;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;

import java.util.ArrayList;

public class InstagramAdapter extends QcRecyclerBaseAdapter<YoutubeItem> {

    public InstagramAdapter(QcBaseLifeActivity qQcBaseLifeActivity, QcRecyclerItemListener qcRecyclerItemListener) {
        super(qQcBaseLifeActivity, qcRecyclerItemListener);
    }

    @Override
    protected void needInitToOnCreate() {
        itemList = new ArrayList<>();
    }

    @Override
    protected void needResetData() {
        itemList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    protected int needLayoutIdFromItemViewType(int position) {
        return R.layout.row_instagram;
    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        YoutubeItem item = (YoutubeItem) object;
        RowInstagramBinding hoderBinding = (RowInstagramBinding) holder.getBinding();
        QcLog.e("needUIBinding === " + item.toString());

        hoderBinding.tvTitle.setText(item.getSnippet().getTitle());
        hoderBinding.tvDescription.setText(item.getSnippet().getDescription());
    }

    @Override
    protected void needUIHeaderBinding(QcBaseViewHolder holder, int position, Object object) {

    }

    @Override
    protected void needUILoadFailBinding(QcBaseViewHolder holder, int position, Object object) {

    }

    @Override
    protected void needUILoadProgressBinding(QcBaseViewHolder holder, int position, Object object) {

    }

    @Override
    protected void needUIOtherBinding(QcBaseViewHolder holder, int position, Object object) {
        QcLog.e("needUIOtherBinding === " );
    }
}

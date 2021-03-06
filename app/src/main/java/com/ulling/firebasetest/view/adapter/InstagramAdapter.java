package com.ulling.firebasetest.view.adapter;

import android.view.View;

import com.squareup.picasso.Picasso;
import com.ulling.firebasetest.R;
import com.ulling.firebasetest.databinding.RowInstagramBinding;
import com.ulling.firebasetest.entites.instagram.Node;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.ui.QcBaseLifeActivity;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.viewutil.adapter.QcBaseViewHolder;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;

import java.util.ArrayList;

/**
 *
 */
public class InstagramAdapter extends QcRecyclerBaseAdapter<Node> {

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
        Node item = (Node) object;
        RowInstagramBinding hoderBinding = (RowInstagramBinding) holder.getBinding();

        hoderBinding.tvTitle.setText(item.getId());
        hoderBinding.tvDescription.setText(item.getEdgeMediaToCaption().getEdges().get(0).getNode().getText());

        Picasso.get()
                .load(item.getThumbnailResources().get(1).getSrc())
                .into(hoderBinding.imgThumbnails);

        hoderBinding.root.setTag(position);
        hoderBinding.root.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                int position_ = (int) v.getTag();
                Node selectecItem = (Node) needItemFromPosition(position_);
                QcLog.e("CLick ==== " + selectecItem.toString());
                QcToast.getInstance().show(selectecItem.toString(), false);
            }
        });
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
        QcLog.e("needUIOtherBinding === ");
    }
}

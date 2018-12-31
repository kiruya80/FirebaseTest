package com.ulling.firebasetest.view.adapter;

import android.view.View;

import com.squareup.picasso.Picasso;
import com.ulling.firebasetest.R;
import com.ulling.firebasetest.common.Define;
import com.ulling.firebasetest.entites.youtube.YoutubeItem;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.ui.QcBaseLifeActivity;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.viewutil.adapter.QcBaseViewHolder;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;

import com.ulling.firebasetest.databinding.RowYoutubeBinding;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class YoutubeAdapter extends QcRecyclerBaseAdapter<YoutubeItem> {
    private final SimpleDateFormat simpleDate;

    public YoutubeAdapter(QcBaseLifeActivity qQcBaseLifeActivity, QcRecyclerItemListener qcRecyclerItemListener) {
        super(qQcBaseLifeActivity, qcRecyclerItemListener);
//        simpleDate = new SimpleDateFormat("MM-dd hh:mm:ss", Locale.KOREA);
        simpleDate = new SimpleDateFormat(Define.dateFormatFrom);
//        simpleDate = new SimpleDateFormat(Define.dateFormatFrom, Locale.KOREA);
//        simpleDate.setTimeZone(TimeZone.getTimeZone("GMT+9"));
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
        return R.layout.row_youtube;
    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        YoutubeItem item = (YoutubeItem) object;
        RowYoutubeBinding hoderBinding = (RowYoutubeBinding) holder.getBinding();
        QcLog.e("needUIBinding === " + item.toString());

        hoderBinding.tvTitle.setText(item.getSnippet().getTitle());
        hoderBinding.tvDescription.setText(item.getSnippet().getDescription());


        QcLog.e("getPublishedAt === " + item.getSnippet().getPublishedAt());
        hoderBinding.tvPublishedAt.setText(item.getSnippet().getPublishedAt());
//        hoderBinding.tvPublishedAt.setText(simpleDate.format(item.getSnippet().getPublishedAt()));



        QcLog.e("getThumbnails === " + item.getSnippet().getThumbnails().getDefault().toString());
//        PicassoUtils.getInstance()
//                .RatioPicasso(item.getSnippet().getThumbnails().getDefault().getUrl(),
//                        hoderBinding.imgThumbnails
//                        maxPictureSize / 2,
//                        imageFiles.get(0).getWidth() / 2,
//                        imageFiles.get(0).getHeight() / 2);

        Picasso.get()
                .load(item.getSnippet().getThumbnails().getMedium().getUrl())
                .into(hoderBinding.imgThumbnails);

//        if (item.getTimestamp() > 0) {
//            hoderBinding.tvTime.setText("Time : " + simpleDate.format(item.getTimestamp()));
//        }
        hoderBinding.root.setTag(position);
        hoderBinding.root.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                int position_ = (int) v.getTag();
                YoutubeItem selectecItem = (YoutubeItem) needItemFromPosition(position_);
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
        QcLog.e("needUIOtherBinding === " );
    }
}

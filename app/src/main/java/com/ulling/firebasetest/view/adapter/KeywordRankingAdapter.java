package com.ulling.firebasetest.view.adapter;

import android.view.View;

import com.ulling.firebasetest.R;
import com.ulling.firebasetest.databinding.RowKeywordRankingBinding;
import com.ulling.firebasetest.entites.KeywordRanking;
import com.ulling.firebasetest.entites.naver.NaverRanking;
import com.ulling.firebasetest.utils.MoveActivityUtils;
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
public class KeywordRankingAdapter extends QcRecyclerBaseAdapter<KeywordRanking> {

    public KeywordRankingAdapter(QcBaseLifeActivity qQcBaseLifeActivity, QcRecyclerItemListener qcRecyclerItemListener) {
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
        return R.layout.row_keyword_ranking;
    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        KeywordRanking item = (KeywordRanking) object;
        RowKeywordRankingBinding hoderBinding = (RowKeywordRankingBinding) holder.getBinding();

        hoderBinding.tvTitle.setText(item.getRanking() + ".  " + item.getKeyword());

        hoderBinding.root.setTag(position);
        hoderBinding.root.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                int position_ = (int) v.getTag();
                KeywordRanking selectecItem = (KeywordRanking) needItemFromPosition(position_);
                QcLog.e("CLick ==== " + selectecItem.toString());
                QcToast.getInstance().show(selectecItem.toString(), false);
//                if (position_ % 2 == 0) {
//                    MoveActivityUtils.moveYoutubeActivity(qQcBaseLifeActivity, selectecItem.getKeyword());
//                } else {
//                    MoveActivityUtils.moveInstagramActivity(qQcBaseLifeActivity, selectecItem.getKeyword());
//                }
                MoveActivityUtils.moveYoutubeActivity(qQcBaseLifeActivity, selectecItem.getKeyword());
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

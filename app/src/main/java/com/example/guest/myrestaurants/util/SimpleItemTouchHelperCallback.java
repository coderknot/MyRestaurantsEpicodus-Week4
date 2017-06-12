package com.example.guest.myrestaurants.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final   ItemTouchHelperAdapter mItemTouchAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter itemTouchAdapter) {
        mItemTouchAdapter = itemTouchAdapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder sourceViewHolder,
                          RecyclerView.ViewHolder targetViewHolder) {
        if(sourceViewHolder.getItemViewType() != targetViewHolder.getItemViewType()) {
            return false;
        }

        mItemTouchAdapter.onItemMove(sourceViewHolder.getAdapterPosition(), targetViewHolder.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        mItemTouchAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}

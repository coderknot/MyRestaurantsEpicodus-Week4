package com.example.guest.myrestaurants.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final ItemTouchHelperAdapter mItemTouchAdapter;

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

    // This method triggers the callback in ItemTouchHelperViewHolder which is then sent to our
    // RestaurantListViewHolder where we will later add animations
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

        // This conditional ensures we only change appearance of active items:
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if(viewHolder instanceof ItemTouchHelperViewHolder) {

                // This tells the View Holder (viewHolder) that an item is being moved or dragged:
                ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                itemViewHolder.onItemSelected();
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    // This method triggers the callback in the ItemTouchHelperViewHolder which will be sent to our RestaurantListViewHolder.
    // Then, in the clearView override in RestaurantListViewHolder, we will remove the animations attached to 'selected'
    // items, since this item will no longer be actively selected
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if(viewHolder  instanceof ItemTouchHelperViewHolder) {

            // Tells the ViewHolder (viewHolder) to return the item back to its normal appearance
            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
            itemViewHolder.onItemClear();
        }
    }
}

package com.example.guest.myrestaurants.Adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.guest.myrestaurants.models.Restaurant;
import com.example.guest.myrestaurants.util.ItemTouchHelperAdapter;
import com.example.guest.myrestaurants.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class FirebaseRestaurantListAdapter
        extends FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder>
        implements ItemTouchHelperAdapter {

    private DatabaseReference mDatabaseRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseRestaurantListAdapter(Class<Restaurant> modelClass,
                                         int modelLayout,
                                         Class<FirebaseRestaurantViewHolder> viewHolderClass,
                                         Query queryRef,
                                         OnStartDragListener onStartDragListener,
                                         Context context) {
        super(modelClass, modelLayout, viewHolderClass, queryRef);
        mDatabaseRef = queryRef.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

    @Override
    protected void populateViewHolder(final FirebaseRestaurantViewHolder viewHolder, Restaurant model, int position) {
        viewHolder.bindRestaurant(model);
        viewHolder.restaurantImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}

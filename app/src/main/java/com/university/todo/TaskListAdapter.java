package com.university.todo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gg on 9/23/2018.
 */

class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.VH> {

    List<TaskModel> mList = new ArrayList();
    ItemClickCallback mCallback = null;
    ItemClickLongCallback mLongCallback = null;



    /**
     * get model for each row
     * */
    private TaskModel getItem(int position){
        return mList.get(position);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_adapter, parent, false);
        return new VH(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position)  {
        TaskModel model =  getItem(position);
        //you magic here...

        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.time.setText(model.getTime());


        if (model.isDone()){
            holder.img.setImageResource(R.drawable.done);
        }else
            switch (model.getPriority()){
                case 0:
                    holder.img.setImageResource(R.drawable.ic_important);break;
                case 1:
                    holder.img.setImageResource(R.drawable.ic_medium);break;
            }
        //click listener
        if(mCallback != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onItemClick(view, getItem(position), position, "");
                }
            });
        }

        //Long click listener
        if(mLongCallback != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongCallback.onItemLongClick(v, getItem(position), position, "");
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    /***************************classes******************************/

    /**
     * ViewHolder class
     * */
    class VH extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title;
        TextView desc;
        TextView time;

        public VH(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.itemImg);
            title = itemView.findViewById(R.id.itemTitle);
            desc = itemView.findViewById(R.id.itemDesc);
            time = itemView.findViewById(R.id.itemTime);
        }
    }


    /**
     * on item click callback
     * */
    interface ItemClickCallback<M> {
        void onItemClick(View view, M model, int position,String data);
    }
    interface ItemClickLongCallback<M> {
        void onItemLongClick(View view, M model, int position,String data);
    }
}
package com.android.sahal.sahalapplication.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sahal.sahalapplication.Model.ModuleComment;
import com.android.sahal.sahalapplication.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder>  {
    private Context mContext;
    private List<ModuleComment> moduleCommentList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView comment , name;

        public MyViewHolder(View view) {
            super(view);
            comment = (TextView) view.findViewById(R.id.txtComment);
            name=(TextView) view.findViewById(R.id.txtComName);

        }
    }


    public CommentAdapter(Context mContext, List<ModuleComment> moduleCommentList) {
        this.mContext = mContext;
        this.moduleCommentList = moduleCommentList;
    }




    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_view_recy, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final CommentAdapter.MyViewHolder holder, final int position) {
        final ModuleComment comment = moduleCommentList.get(position);
        String sss=comment.getSenderName();
        holder.name.setText(comment.getSenderName());
        holder.comment.setText(comment.getMessage());
        /*holder.title.setText(item.getName());
        holder.desc.setText(item.getDescription());
        holder.count.setText(item.getPrice() + " SR");*/


    }






    @Override
    public int getItemCount() {
        return moduleCommentList.size();
    }






    public interface onItemClickListener{
        public void itemDetailClick(ModuleComment item);



    }




}

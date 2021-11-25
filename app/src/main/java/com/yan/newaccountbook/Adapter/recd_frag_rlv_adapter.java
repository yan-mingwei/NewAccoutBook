package com.yan.newaccountbook.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yan.newaccountbook.R;
import com.yan.newaccountbook.bean.TypeBean;

import java.util.List;

/**
 * 记账页面中碎片中列表的 适配器
 */
public class recd_frag_rlv_adapter extends RecyclerView.Adapter<recd_frag_rlv_adapter.ViewHolder> {

    public interface OnClickListener{
        void onRefreshView(TypeBean typeBean,int pos);
    }

    OnClickListener onClickListener;

    Context context;
    private List<TypeBean> typeBeans;

    int pos=0;  //当前被选中的位置

    public void setPos(int pos) {
        this.pos = pos;
    }

    public recd_frag_rlv_adapter(Context context, List<TypeBean> typeBeans, OnClickListener onClickListener) {
        this.context = context;
        this.typeBeans = typeBeans;
        this.onClickListener=onClickListener;
    }

    //设置布局控件
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recd_frag_rlv,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }


    //给控件设置数据和事件响应等
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TypeBean typeBean=typeBeans.get(position);
        holder.textView.setText(typeBean.getTypeName());
        if(pos==position){
            holder.imageView.setImageResource(typeBean.getsImageId());
        }else{
            holder.imageView.setImageResource(typeBean.getImageId());
        }
        /**
         * 事件响应，传参又很难呀，又要基于接口回调； 点击事件发生，fragment界面需要变化
         */
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onRefreshView(typeBean,position);
            }
        });

    }

    @Override
    public int getItemCount()


    {
        return typeBeans.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.item_recd_frag_rlv_liLayout);
            imageView=itemView.findViewById(R.id.item_recd_frag_rlv_iv);
            textView=itemView.findViewById(R.id.item_recd_frag_rlv_tv);
        }
    }

}

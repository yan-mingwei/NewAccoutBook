package com.yan.newaccountbook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yan.newaccountbook.R;
import com.yan.newaccountbook.bean.AccountBean;

import java.util.List;


public class account_rlv_adapter extends RecyclerView.Adapter<account_rlv_adapter.ViewHolder> {


    Context context;
    private List<AccountBean> accountBeanList;

    public account_rlv_adapter(Context context, List<AccountBean> accountBeanList) {
        this.context = context;
        this.accountBeanList = accountBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_rlv,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccountBean accountBean = accountBeanList.get(position);
        holder.imageView.setImageResource(accountBean.getsImageId());
        holder.typeTv.setText(accountBean.getTypeName());
        holder.remarkTv.setText(accountBean.getRemark());
        if(accountBean.getKind()==0){
            holder.moneyTv.setText("￥ -" + accountBean.getMoney());
        }else{
            holder.moneyTv.setText("￥" + accountBean.getMoney());
        }
        holder.timeTv.setText(accountBean.getTime());
    }

    @Override
    public int getItemCount() {
        return accountBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        ImageView imageView;
        TextView typeTv,remarkTv,moneyTv,timeTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.item_main_rlv_layout);
            imageView=itemView.findViewById(R.id.item_main_rlv_iv);
            typeTv=itemView.findViewById(R.id.item_main_rlv_type);
            remarkTv=itemView.findViewById(R.id.item_main_rlv_remark);
            moneyTv=itemView.findViewById(R.id.item_main_rlv_money);
            timeTv=itemView.findViewById(R.id.item_main_rlv_time);
        }
    }
}
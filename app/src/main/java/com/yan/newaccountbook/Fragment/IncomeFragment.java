package com.yan.newaccountbook.Fragment;

import com.yan.newaccountbook.Adapter.recd_frag_rlv_adapter;
import com.yan.newaccountbook.R;
import com.yan.newaccountbook.bean.TypeBean;
import com.yan.newaccountbook.db.DBManger;

import java.util.ArrayList;
import java.util.List;

/**
 * 面向对象还不熟悉
 */
public class IncomeFragment extends BaseRecordFragment{

    @Override
    public void loadDataToRlv() {
        super.loadDataToRlv();  //调用父类原有的方法，避免重复代码
        List<TypeBean> inList= DBManger.getTypeList(1);
        typeList.addAll(inList);
        rlv_adapter.notifyDataSetChanged();

        typeTv.setText("其它");
        typeIv.setImageResource(R.mipmap.in_qt_fs);
    }

    @Override
    public void saveAccountToDB() {
        super.saveAccountToDB();
        accountBean.setKind(1);
        DBManger.insertItemToAccountTB(accountBean);
    }
}

package com.yan.newaccountbook.Fragment;

import com.yan.newaccountbook.Adapter.recd_frag_rlv_adapter;
import com.yan.newaccountbook.R;
import com.yan.newaccountbook.bean.TypeBean;
import com.yan.newaccountbook.db.DBManger;

import java.util.ArrayList;
import java.util.List;

public class OutcomeFragment extends BaseRecordFragment{

    @Override
    public void loadDataToRlv() {
        super.loadDataToRlv();
        List<TypeBean> outList= DBManger.getTypeList(0);
        typeList.addAll(outList);
        rlv_adapter.notifyDataSetChanged();

        typeTv.setText("其它");
        typeIv.setImageResource(R.mipmap.ic_qita_fs);
    }
}

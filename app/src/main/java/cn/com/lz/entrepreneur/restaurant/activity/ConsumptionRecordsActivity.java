package cn.com.lz.entrepreneur.restaurant.activity;

import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import cn.com.lz.entrepreneur.restaurant.R;

/**
 * Description:消费记录页面
 * author: lhd
 * Date: 2018/3/20
 */

public class ConsumptionRecordsActivity extends BaseActivity{

    // 消费记录列表
    @BindView(R.id.consumption_recycler_view)
    RecyclerView mConsumptionList;

    @Override
    protected int getContentView() {
        return R.layout.activity_consumption_records;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListener() {

    }
}

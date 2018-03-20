package cn.com.lz.entrepreneur.restaurant.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import cn.com.lz.entrepreneur.restaurant.R;

/**
 * Description:消费记录页面
 * author: lhd
 * Date: 2018/3/20
 */

public class ConsumptionRecordsActivity extends BaseActivity implements View.OnClickListener {

    // 返回
    @BindView(R.id.back)
    ImageButton mBack;
    // 标题
    @BindView(R.id.title)
    TextView mTitle;
    // 消费记录列表
    @BindView(R.id.consumption_recycler_view)
    RecyclerView mConsumptionList;

    @Override
    protected int getContentView() {
        return R.layout.activity_consumption_records;
    }

    @Override
    protected void init() {
        mTitle.setText(R.string.consumption_records);
    }

    @Override
    protected void setListener() {
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}

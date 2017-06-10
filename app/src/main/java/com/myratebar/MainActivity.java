package com.myratebar;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myratebar.widget.BaseBar;
import com.myratebar.widget.MyRateBar;

public class MainActivity extends AppCompatActivity implements BaseBar.BaseBarListener {
    TextView tv;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout s= (LinearLayout) findViewById(R.id.ss);
        tv= (TextView) findViewById(R.id.tv);
        MyRateBar myRateBar=new MyRateBar(this,new String[]{"2","4","6","8","6","4","2"},new int[]{10,11,12,13,14,15,16});
        myRateBar.setls(this);
        s.addView(myRateBar,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

    }

    @Override
    public void OnSelectChanged(View v, int index,float num) {
        tv.setText("当前选择下标:"+index+" 当前选择分数"+num);
    }

}

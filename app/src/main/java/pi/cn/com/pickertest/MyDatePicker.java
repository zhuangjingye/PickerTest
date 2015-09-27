package pi.cn.com.pickertest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by pi on 15-9-27.
 */
public class MyDatePicker extends LinearLayout {

    private Context context;

    private PickerView yearView;//年

    private PickerView monthView;//月

    private PickerView dayView;//日

    private ArrayList<String> yearlist = new ArrayList<String>();

    private ArrayList<String> monthlist = new ArrayList<String>();

    private ArrayList<String> dayList = new ArrayList<String>();

    public MyDatePicker(Context context) {
        super(context);
        initView(context);
    }

    public MyDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 初始化view
     * @param context
     */
    private void initView(Context context) {
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.view_datepicker,this);
        yearView = (PickerView) view.findViewById(R.id.year_pv);
        monthView = (PickerView) view.findViewById(R.id.month_pv);
        dayView = (PickerView) view.findViewById(R.id.day_pv);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (int i = 1901; i < 2900; i++) {
            yearlist.add(i + "");
        }
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                monthlist.add("0"+i);
            } else {
                monthlist.add(i+"");
            }
        }

        yearView.setData(yearlist);
        yearView.setLable("年");
        monthView.setData(monthlist);
        monthView.setLable("月");
        dayView.setData(getDaylist(31));
        dayView.setLable("日");
        setDate(Calendar.getInstance());
        setListener();
    }

    /**
     * 设置listener
     */
    private void setListener() {

        yearView.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                Toast.makeText(context, " " + text + " ",
                        Toast.LENGTH_SHORT).show();
                setDayList();
            }
        });

        monthView.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                Toast.makeText(context, " " + text + " ",
                        Toast.LENGTH_SHORT).show();
                setDayList();
            }
        });


        dayView.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                Toast.makeText(context, " " + text + " ",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<String> getDaylist(int num) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 1; i <= num; i++) {
            String d = i + "";
            if (i < 10) {
                d = "0" + i;
            }
            list.add(d);
        }
        return list;
    }


    private void setDayList() {
        int currentYear = yearView.getCurrentSelected();
        int currentMonth = monthView.getCurrentSelected();
        switch (currentMonth) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                dayList = getDaylist(31);
                break;
            case 2:
                if (currentYear % 4 == 0) {
                    dayList = getDaylist(29);
                } else {
                    dayList = getDaylist(28);
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                dayList = getDaylist(30);
                break;
            default:
                break;
        }
        dayView.setData(dayList);
    }

    /**
     * 设置初始日期
     * @param c
     */
    public void setDate(Calendar c) {
        int y = c.get(Calendar.YEAR);
        int index1 = y - 1901;
        yearView.setSelected(index1);
        int m = c.get(Calendar.MONTH);
        monthView.setSelected(m);
        int d = c.get(Calendar.DAY_OF_MONTH);
        dayView.setSelected(d - 1);
    }
}

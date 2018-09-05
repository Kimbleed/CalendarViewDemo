package com.example.a49479.calendarviewdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class YdCatCalendatView  extends LinearLayout {

    /**
     * 布局
     */
    TextView tv_calendar;
    RecyclerView customRecycler_calendar;

    /**
     * List相关
     */
    CEn7DayRecyclerAdapterNew mCalendarDayAdapter;
    List<CalendarDate> mCalendarDay = new ArrayList<>();

    /**
     * 数值设置
     */
    public static final int DEFAULT_SHOW_DAYS = 30;     //默认展示天数
    private int mShowDaysBeforeNow = DEFAULT_SHOW_DAYS;          //展示天数
    private int mShowDaysAfterNow = DEFAULT_SHOW_DAYS;

    private long mCurrentTimeMillis = System.currentTimeMillis(); //最后一天，默认今天

    private static final String[] arrMonth = new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月",};

    public static final long ONE_DAY_TIME = 1000L * 60L * 60L * 24L;

    public YdCatCalendatView(Context context) {
        super(context);
        initView(context);
    }

    public YdCatCalendatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public YdCatCalendatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化View
     */
    public void initView(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_cen_calendar_view_new, null);
        addView(view);
        tv_calendar = (TextView) view.findViewById(R.id.tv_calendar);
        customRecycler_calendar = (RecyclerView) view.findViewById(R.id.customRecycler_calendar);
        initRecycler(context);
    }

    /**
     * 初始化RecyclerView
     */
    public void initRecycler(Context context) {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,7);
        customRecycler_calendar.setLayoutManager(layoutManager);

        CalendarDate now = DateUtils.getCalendarDate(mCurrentTimeMillis);
        tv_calendar.setText(now.year+"-"+now.month);
        CalendarDate firstDay = DateUtils.getCalendarDate(mCurrentTimeMillis-(Integer.parseInt(now.day)-1)*ONE_DAY_TIME);
        int plus = Integer.parseInt(firstDay.way);

        mShowDaysBeforeNow = (plus +Integer.parseInt(now.day)-1);
        for (long i = 0; i < mShowDaysBeforeNow; i++) {
            long time = mCurrentTimeMillis - ONE_DAY_TIME * i;
            CalendarDate date = DateUtils.getCalendarDate(time);
            mCalendarDay.add(0, date);
        }

        for(int j=0;;j++){
            long time = mCurrentTimeMillis + ONE_DAY_TIME * (j+1);
            CalendarDate date = DateUtils.getCalendarDate(time);
            if(now.month.equals(date.month)) {
                mCalendarDay.add(date);
            }
            else{
                break;
            }
        }

        CalendarDate last = mCalendarDay.get(mCalendarDay.size()-1);
        int plusAfter = 7-Integer.parseInt(last.way);
        for(int k = 0;k<plusAfter;k++){
            long time = last.time + ONE_DAY_TIME * (k+1);
            CalendarDate date = DateUtils.getCalendarDate(time);
            mCalendarDay.add(date);
        }

        mCalendarDayAdapter = new CEn7DayRecyclerAdapterNew(context, mCalendarDay,now);

        //选择（高亮）某一日期
        customRecycler_calendar.setAdapter(mCalendarDayAdapter);
        customRecycler_calendar.scrollToPosition(mCalendarDay.size() - 1);
        mCalendarDayAdapter.chooseDate(now);
    }


    /**
     * 获取当前高亮（选择）日期
     *
     * @return
     */
    public CalendarDate getChoosedDate() {
        return mCalendarDay.get(mCalendarDayAdapter.getChooseDate());
    }

    /**
     * 点击日期时的监听
     *
     * @param listener
     */
    public void setOnChooseListener(CEn7DayRecyclerAdapterNew.CEn7DayListener listener) {
        mCalendarDayAdapter.setListener(listener);
    }

    /**
     * 设置为特殊日期
     */
    public void setSpecialDate(int position) {
        mCalendarDayAdapter.setHaveDay(position);
    }

    /**
     * 获取日期list
     */
    public List<CalendarDate> getCalendarDays() {
        return mCalendarDay;
    }

    /**
     * 通过CalendarDate 来获取 index
     * @param date
     * @return
     */
    public int getCalendarDayIndex(CalendarDate date) {
        int size = mCalendarDay.size();
        for (int i = 0; i < size; i++) {
            CalendarDate calendarDate = mCalendarDay.get(i);
            //判定是否同一天
            if (calendarDate.getDateFormat("yyyyMMdd").equals(date.getDateFormat("yyyyMMdd"))) {
                return i;
            }
        }
        return -1;
    }

}

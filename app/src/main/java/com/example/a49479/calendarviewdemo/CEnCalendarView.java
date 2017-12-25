package com.example.a49479.calendarviewdemo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 49479 on 2017/12/21.
 *
 * 横条展示一周的日历控件
 */

public class

CEnCalendarView extends LinearLayout {

    /**
     * 布局
     */
    TextView tv_calendar;
    RecyclerView customRecycler_calendar;


    /**
     * List相关
     */
    CEn7DayRecyclerAdapter mCalendarDayAdapter;
    List<CalendarDate> mCalendarDay = new ArrayList<>();

    /**
     * 数值设置
     */
    public static final int DEFAULT_SHOW_DAYS = 60;     //默认展示天数
    private int mShowDays = DEFAULT_SHOW_DAYS;          //展示天数

    private long mLastDayTime = System.currentTimeMillis(); //最后一天，默认今天

    private static final String[] arrMonth = new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月",};

    public static final long ONE_DAY_TIME = 1000L * 60L * 60L * 24L;


    public CEnCalendarView(Context context) {
        super(context);
        initView(context);
    }

    public CEnCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CEnCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化View
     */
    public void initView(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_cen_calendar_view, null);
        addView(view);
        tv_calendar = (TextView) view.findViewById(R.id.tv_calendar);
        customRecycler_calendar = (RecyclerView) view.findViewById(R.id.customRecycler_calendar);
        initRecycler(context);
    }

    /**
     * 初始化RecyclerView
     */
    public void initRecycler(Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        customRecycler_calendar.setLayoutManager(linearLayoutManager);

        //显示最近60天
        for (long i = 0; i < mShowDays; i++) {
            long time = mLastDayTime - ONE_DAY_TIME * i;
            CalendarDate date = DateUtils.getCalendarDate(time);
            mCalendarDay.add(0, date);
        }
        mCalendarDayAdapter = new CEn7DayRecyclerAdapter(context, mCalendarDay);

        //选择（高亮）某一日期
        customRecycler_calendar.setAdapter(mCalendarDayAdapter);
        customRecycler_calendar.scrollToPosition(mCalendarDay.size() - 1);
        mCalendarDayAdapter.chooseDate(mCalendarDay.size() - 1);

        //日历设置滚动监听
        customRecycler_calendar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (mCalendarDay.get(manager.findFirstCompletelyVisibleItemPosition()).getMonth().equals(mCalendarDay.get(manager.findLastCompletelyVisibleItemPosition()).getMonth())) {
                    tv_calendar.setText(arrMonth[Integer.parseInt(mCalendarDay.get(manager.findFirstCompletelyVisibleItemPosition()).getMonth())]);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (mCalendarDay.get(manager.findFirstCompletelyVisibleItemPosition()).getMonth().equals(mCalendarDay.get(manager.findLastCompletelyVisibleItemPosition()).getMonth())) {
                    tv_calendar.setText(arrMonth[Integer.parseInt(mCalendarDay.get(manager.findFirstCompletelyVisibleItemPosition()).getMonth())]);
                }
                String s = mCalendarDay.get(manager.findLastCompletelyVisibleItemPosition()).getMonth();
                String b = DateUtils.getMonth(System.currentTimeMillis());
                if (s.equals(b)) {
                    tv_calendar.setText(arrMonth[Integer.parseInt(mCalendarDay.get(manager.findLastCompletelyVisibleItemPosition()).getMonth())]);
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
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
    public void setOnChooseListener(CEn7DayRecyclerAdapter.CEn7DayListener listener) {
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

package com.example.a49479.calendarviewdemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.qiujuer.genius.ui.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by 49479 on 2017/8/29.
 */

public class CEn7DayRecyclerAdapterNew extends RecyclerView.Adapter<CEn7DayRecyclerAdapterNew.CEn7DayViewHolder> {

    private Context CTX;
    private LayoutInflater mInflater;
    private List<CalendarDate> mData = new ArrayList<>();
    private List<Boolean> mHaveRecordDay = new ArrayList<>();
    private List<Boolean> mHaveEventDay = new ArrayList<>();
    private CEn7DayListener listener;

    private CalendarDate mToday;

    //翻到的月份
    private String mCurrentMonth;
    private String mCurrentYear;

    private boolean[] mChose;

    private CalendarDate mChooseDate;

    public static final String[] arrWeek = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public CEn7DayRecyclerAdapterNew(Context context, List<CalendarDate> data, CalendarDate today) {
        CTX = context;
        mInflater = LayoutInflater.from(CTX);
        mData = data;
        mToday = today;
        mCurrentMonth = mToday.month;
        mCurrentYear = mToday.year;
        mChose = new boolean[mData.size()];
        for (int i = 0; i < mData.size(); i++) {
            mHaveRecordDay.add(false);
            mHaveEventDay.add(false);
        }
        for (int i = (mData.size() - 1); i >= 0; i--) {
            if (mData.get(i).getMonth().equals(getMonth(System.currentTimeMillis()))
                    && mData.get(i).getDay().equals(getDay(System.currentTimeMillis()))) {
                Log.i("compare", "" + mData.get(i).getMonth() + "    " + (mData.get(i).getDay()));
                mChose[i] = true;
                break;
            }
        }

    }

    public void setHaveRecordDay(int position) {
        mHaveRecordDay.set(position, true);
    }

    public void setHaveRecordDay(CalendarDate date) {
        for (int i = 0; i < mData.size(); i++) {
            CalendarDate d = mData.get(i);
            if (d.month.equals(date.month) && d.day.equals(date.day)) {
                setHaveRecordDay(i);
            }
        }
    }

    public void setHaveEventDay(int position) {
        mHaveEventDay.set(position, true);
    }

    public void setHaveEventDay(CalendarDate date) {
        for (int i = 0; i < mData.size(); i++) {
            CalendarDate d = mData.get(i);
            if (d.month.equals(date.month) && d.day.equals(date.day)) {
                setHaveEventDay(i);
            }
        }
    }

    public void setListener(CEn7DayListener listener) {
        this.listener = listener;
    }

    @Override
    public CEn7DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_week_new, parent, false);
//        WindowManager wm = (WindowManager)CTX.getSystemService(Context.WINDOW_SERVICE);
//        int screenWidth = wm.getDefaultDisplay().getWidth();
        view.getLayoutParams().width = parent.getMeasuredWidth() / 7;
        CEn7DayViewHolder viewHolder = new CEn7DayViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CEn7DayViewHolder holder, final int position) {
        Log.i("CEnCalendar", "onBindViewHolder position: " + position);
        CalendarDate date = mData.get(position);
        holder.tv_date.setText(mData.get(position).getDay());
        holder.tv_way.setText(arrWeek[Integer.parseInt(date.getWay()) - 1]);

        //被选中的日期
        if (date.equalsDate(mChooseDate)) {
            holder.view_choose.setVisibility(View.VISIBLE);
            holder.tv_date.setTextColor(CTX.getResources().getColor(R.color.loock_yellow_alpha));
        } else {
            holder.view_choose.setVisibility(View.INVISIBLE);
            holder.tv_date.setTextColor(CTX.getResources().getColor(R.color.title_text_color_grey1));
        }

        //今天
        if (date.equalsDate(mToday)) {
            holder.view_locate.setVisibility(View.VISIBLE);
        } else {
            holder.view_locate.setVisibility(View.INVISIBLE);
        }

        //日历翻到的月份
        if (date.year.equals(mCurrentYear) && date.month.equals(mCurrentMonth)) {
            holder.tv_date.setTextColor(Color.parseColor("#444444"));
        } else {
            holder.tv_date.setTextColor(Color.parseColor("#aaaaaa"));
        }

        //该日有无记录
        if (mHaveRecordDay.get(position)) {
            holder.view_flag_1.setVisibility(View.VISIBLE);
        } else {
            holder.view_flag_1.setVisibility(View.INVISIBLE);
        }

        //该日有无事件
        if (mHaveEventDay.get(position)) {
            holder.view_flag_2.setVisibility(View.VISIBLE);
        } else {
            holder.view_flag_2.setVisibility(View.INVISIBLE);
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate(position);
                if (listener != null)
                    listener.onClickItem(position, view);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.i("CEnCalendar", "getItemCount " + mData.size());
        return mData.size();
    }

    class CEn7DayViewHolder extends RecyclerView.ViewHolder {
        public CEn7DayViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_way = (TextView) itemView.findViewById(R.id.tv_way);
            view_choose = itemView.findViewById(R.id.view_choose);
            view_locate = itemView.findViewById(R.id.view_locate);
            view_flag_1 = itemView.findViewById(R.id.view_flag_1);
            view_flag_2 = itemView.findViewById(R.id.view_flag_2);
            btn =  (Button) itemView.findViewById(R.id.btn);
        }

        View itemView;
        Button btn;
        TextView tv_date;
        TextView tv_way;
        View view_choose;
        View view_locate;
        View view_flag_1;
        View view_flag_2;
    }

    public interface CEn7DayListener {
        void onClickItem(int position, View view);
    }

    public String getDay(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        return day;
    }

    public String getMonth(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        return month;
    }

    public void chooseDate(int position) {
        for (int i = 0; i < mChose.length; i++) {
            mChose[i] = false;
        }
        mChooseDate = mData.get(position);
        mChose[position] = true;
        notifyDataSetChanged();
    }

    public void chooseDate(CalendarDate date) {
        for (int i = 0; i < mChose.length; i++) {
            mChose[i] = false;
        }
        for (int i = 0; i < mData.size(); i++) {
            CalendarDate date1 = mData.get(i);
            if (date1.month.equals(date.month) && date1.day.equals(date.day)) {
                mChooseDate = date1;
                mChose[i] = true;
            }
        }
        notifyDataSetChanged();
    }

    public int getChooseDate() {
        for (int i = 0; i < mChose.length; i++) {
            if (mChose[i])
                return i;
        }
        return -1;
    }

    private void reset(String year,String month) {
        mCurrentYear =year;
        mCurrentMonth = month;
        mChose = new boolean[mData.size()];
        mHaveEventDay.clear();
        mHaveRecordDay.clear();
        for (int i = 0; i < mData.size(); i++) {
            mHaveRecordDay.add(false);
            mHaveEventDay.add(false);
        }
    }

    public void resetWithNotifyData(String year,String month) {
        reset(year,month);
        notifyDataSetChanged();
    }
}

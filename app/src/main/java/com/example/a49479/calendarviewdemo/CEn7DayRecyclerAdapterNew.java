package com.example.a49479.calendarviewdemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private String mMonth;
    private boolean[] mChose;
    private int mChooseIndex = -1;
    private String mChooseMonth = "";
    public static final String[] arrWeek = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public CEn7DayRecyclerAdapterNew(Context context, List<CalendarDate> data, CalendarDate today) {
        CTX = context;
        mInflater = LayoutInflater.from(CTX);
        mData = data;
        mToday = today;
        mMonth = mToday.month;
        mChose = new boolean[mData.size()];
        for (int i = (mData.size() - 1); i >= 0; i--) {
            if (mData.get(i).getMonth().equals(getMonth(System.currentTimeMillis()))
                    && mData.get(i).getDay().equals(getDay(System.currentTimeMillis()))) {
                Log.i("compare", "" + mData.get(i).getMonth() + "    " + (mData.get(i).getDay()));
                mChose[i] = true;
                break;
            }
        }
        for (int i = 0; i < mData.size(); i++) {
            mHaveRecordDay.add(false);
            mHaveEventDay.add(false);
        }
    }

    public void setHaveRecordDay(int position) {
        mHaveRecordDay.set(position, true);
    }

    public void setHaveEventDay(int position) {
        mHaveEventDay.set(position, true);
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

        if (mMonth.equals(mChooseMonth) && mChooseIndex == position) {
            holder.view_choose.setVisibility(View.VISIBLE);
            holder.tv_date.setTextColor(CTX.getResources().getColor(R.color.loock_yellow_alpha));
        } else {
            holder.view_choose.setVisibility(View.INVISIBLE);
            holder.tv_date.setTextColor(CTX.getResources().getColor(R.color.title_text_color_grey1));
        }

        if (date.month.equals(mToday.month) && date.day.equals(mToday.day)) {
            holder.view_locate.setVisibility(View.VISIBLE);
        } else {
            holder.view_locate.setVisibility(View.INVISIBLE);
        }

        if (date.month.equals(mMonth)) {
            holder.tv_date.setTextColor(Color.parseColor("#444444"));
        } else {
            holder.tv_date.setTextColor(Color.parseColor("#aaaaaa"));
        }

        if (mHaveRecordDay.get(position)) {
            holder.view_flag_1.setVisibility(View.VISIBLE);
        } else {
            holder.view_flag_1.setVisibility(View.INVISIBLE);
        }

        if (mHaveEventDay.get(position)) {
            holder.view_flag_2.setVisibility(View.VISIBLE);
        } else {
            holder.view_flag_2.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        }

        View itemView;
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
        mChooseIndex = position;
        mChooseMonth = mMonth;
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
                mChooseIndex = i;
                mChooseMonth = mMonth;
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

    public void reset(String month) {
        mMonth = month;
        for (int i = 0; i < mChose.length; i++) {
            mChose[i] = false;
        }
        notifyDataSetChanged();
    }
}

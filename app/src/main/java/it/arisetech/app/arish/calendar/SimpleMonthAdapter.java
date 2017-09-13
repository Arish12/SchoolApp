package it.arisetech.app.arish.calendar;

import android.content.Context;

/**
 * Created by Hornet on 5/2/2016.
 */
public class SimpleMonthAdapter extends MonthAdapter {
    public SimpleMonthAdapter(Context context, DatePickerController controller) {
        super(context, controller);
    }

    @Override
    public MonthView createMonthView(Context context) {
        final MonthView monthView = new SimpleMonthView(context, null, mController);
        return monthView;
    }


}

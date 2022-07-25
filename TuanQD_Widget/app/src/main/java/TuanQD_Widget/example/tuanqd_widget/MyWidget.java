package TuanQD_Widget.example.tuanqd_widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import TuanQD_Widget.example.tuanqd_widget.R;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidget extends AppWidgetProvider {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    static int day, month;
    static Context mContext;
    static AppWidgetManager mAppWidgetManager;
    static int mAppWidgetId;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        // get Intent from User Update.
        if (action.equals(MainActivity.UPDATE_ACTION_MAIN)) {
            if (intent.getIntExtra("day", 0) > 0 &&
                    intent.getIntExtra("month", 0) > 0) {
                day = intent.getIntExtra("day", 0);
                month = intent.getIntExtra("month", 0);
                if (mContext != null) {
                    updateAppWidget(mContext, mAppWidgetManager, mAppWidgetId);
                }
            }
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        int k = 0;
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_MUTABLE);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        // calculate remaining Days
        Date date2 = new Date();
        Log.i("date2 ", "" + date2);
        String strDate = day + "/" + month + "/" + 22;
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String formatDate = format.format(new Date());
        try {
            Date date = format.parse(strDate);
            Log.i("date", "" + date);
            if (date.getMonth() < date2.getMonth() || date.getDate()<date2.getYear()) {
                k = 365 - Math.abs((int) (date2.getDate() - date.getDate()
                        + 30 * (date2.getMonth() - date.getMonth())));
            } else {
                k = Math.abs((int) (date2.getDate() - date.getDate()
                        + 30 * (date2.getMonth() - date.getMonth())));
            }
            Log.i("TAG", "" + k);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // set Text
        if (k > 0) {
            views.setTextViewText(R.id.txtDays, String.valueOf(k));
        }
        views.setOnClickPendingIntent(R.id.layoutWidget, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

        // Save Context;
        mContext = context;
        mAppWidgetManager = appWidgetManager;
        mAppWidgetId = appWidgetId;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Log.i("UPDATE", "");
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
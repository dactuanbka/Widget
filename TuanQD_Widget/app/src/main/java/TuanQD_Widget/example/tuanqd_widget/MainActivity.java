package TuanQD_Widget.example.tuanqd_widget;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import TuanQD_Widget.example.tuanqd_widget.R;

public class MainActivity extends AppCompatActivity {
    static final String UPDATE_ACTION_MAIN = "BirthDay";
    EditText mEdtDay, mEdtMonth;
    Button mBtnUpdate;
    int day = 0, month = 0;
    final MyWidget myWidgetBroadcast = new MyWidget();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEdtDay = findViewById(R.id.edtDay);
        mEdtMonth = findViewById(R.id.edtMonth);
        mBtnUpdate = findViewById(R.id.btnUpdate);
        registerBroadcast();

        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEdtDay.getText().toString().isEmpty() &&
                        !mEdtMonth.getText().toString().isEmpty()) {
                    day = Integer.parseInt(mEdtDay.getText().toString().trim());
                    month = Integer.parseInt(mEdtMonth.getText().toString().trim());
                    Log.i("DAY/MONTH",""+day +"/" + month);
                }
                if (day>0 &&day <= 30 && month <= 12 && month>0) {
                    Log.i("DAY/MONTH VALID","");
                    Intent intentUpdate = new Intent(UPDATE_ACTION_MAIN);
                    intentUpdate.putExtra("day", day);
                    intentUpdate.putExtra("month", month);
                    sendBroadcast(intentUpdate);
                } else {
                    Toast.makeText(MainActivity.this, "Nhập lại ngày tháng",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter(UPDATE_ACTION_MAIN);
        this.registerReceiver(myWidgetBroadcast, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(myWidgetBroadcast);

    }
}
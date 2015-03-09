package tigerkid.applab.Plugin1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.DigitalClock;
import android.widget.TextView;

public class EgActivity extends Activity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText("Example Plugin");
        DigitalClock d = (DigitalClock) findViewById(R.id.digitalClock);
        registerReceiver(uiUpdated, new IntentFilter("UI_UPDATED"));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(uiUpdated);
        super.onDestroy();
    }

    public void updateText(String text){
        if(text!=null)
            tv.setText(text);
        else
            tv.setText("Got null");
    }

    public void onClick(View v){
        PluginMainService.callback();
    }
    private BroadcastReceiver uiUpdated = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("Updating textview");
            updateText(intent.getExtras().getString("<KEY>"));
            // tv.setText(intent.getExtras().getString("<KEY>"));
        }
    };
}

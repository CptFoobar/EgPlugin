package tigerkid.applab.Plugin1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DigitalClock;
import android.widget.TextView;

public class EgActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("Example Plugin");
        DigitalClock d = (DigitalClock) findViewById(R.id.digitalClock);
    }
}

package tigerkid.applab.Plugin1;

import android.content.Intent;
import android.util.Log;
import tigerkid.applab.Plugin_Interfaces.FlubbrPlugin;
import tigerkid.applab.Plugin_Interfaces.PluginConfiguration;

public class PluginMainService extends FlubbrPlugin {

    private static final String LOG_TAG = "PluginMainService";

    @Override
    protected void onLoad() {
        Log.i(LOG_TAG, "onLoad");
    }

    @Override
    protected void run() {
        System.out.println("Starting activity");
        Intent i = new Intent(getApplicationContext(), EgActivity.class);
        i.putExtra("PC", rec);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    protected void onUpdate(final PluginConfiguration pluginConfiguration) {
        Log.i(LOG_TAG, "onUpdate: Received updates. They are:");
        pluginConfiguration.describeContents();
       /* EgActivity.runOnUiThread(new Runnable() {
            public void run() {
                EgActivity.updateText(pluginConfiguration.getmString());
            }
        });*/
        if(pluginConfiguration.getmString() == null)
            System.out.println("Bad update!");
        Intent i = new Intent("UI_UPDATED");
        i.putExtra("<KEY>", pluginConfiguration.getmString());

        sendBroadcast(i);
    }

    @Override
    protected void onUnload() {
        Log.i(LOG_TAG, "onUnload");
    }

    public static void callback(){
       requestCallBack("Requested callback", 9);
    }
}
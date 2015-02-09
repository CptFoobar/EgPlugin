package tigerkid.applab.Plugin1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import tigerkid.applab.Plugin_Interfaces.*;

public class PluginMainService extends Service {
    static final String LOG_TAG = "EgPlugin::";
    static final String CATEGORY_ADD_IF = "tigerkid.applab.intent.category.ADD_PLUGIN";
    PluginConfiguration rec;
    private final RemoteCallbackList<IPluginServiceCallback> remoteCallbacks
            = new RemoteCallbackList<IPluginServiceCallback>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        return Service.START_NOT_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return addBinder;
    }

    private final IPluginInterface.Stub addBinder =
            new IPluginInterface.Stub() {
                public void load(PluginConfiguration pluginConfiguration) {
                    try {
                        Log.d(LOG_TAG, "load: Plugin loaded.");
                        if (pluginConfiguration != null) {
                            Log.d(LOG_TAG, "load: Plugin configuration received.");
                            pluginConfiguration.describeContents();
                            rec = pluginConfiguration.getPluginConfiguration(pluginConfiguration);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                public void run() {
                    Log.d(LOG_TAG, "run: Plugin running");
                    System.out.println(rec.describeContents());
                    System.out.println("Starting activity");
                    Intent i = new Intent(getApplicationContext(), EgActivity.class);
                    i.putExtra("PC", rec);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    System.out.println("/**************************************************/");
                    System.out.println("Making callBacks");
                    System.out.println("/**************************************************/");
                    sendNewData(new PluginResponse("callback", 2));
                }

                public void unload() {
                    Log.d(LOG_TAG, "unload: Plugin unloaded");
                    rec = null;
                }

                public void registerCallback(IPluginServiceCallback ipsc) {
                    if (ipsc != null) {
                        remoteCallbacks.register(ipsc);
                        System.out.println("Registered callback");
                    }
                }

                public void unregisterCallback(IPluginServiceCallback ipsc) {
                    if (ipsc != null) {
                        remoteCallbacks.unregister(ipsc);
                        System.out.println("Unregistered callback");
                    }
                }
            };


    private void sendNewData(final PluginResponse info) {
        final int n = remoteCallbacks.beginBroadcast();
        System.out.println("Callback is sending: ");
        info.describeContents();
        for (int i = 0; i < n; i++) {
            final IPluginServiceCallback callback = remoteCallbacks.getBroadcastItem(i);
            try {
                callback.receivedCallBack(info);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Broadcast error", e);
            }
        }
        remoteCallbacks.finishBroadcast();
    }

}

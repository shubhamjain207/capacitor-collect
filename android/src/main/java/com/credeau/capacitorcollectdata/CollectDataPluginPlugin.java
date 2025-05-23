package com.credeau.capacitorcollectdata;

import android.util.Log;
import android.content.Context;

import com.getcapacitor.JSObject;
import com.getcapacitor.JSArray;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Looper;

import com.credeau.collectdevicedata.DataDeviceDataCallback;
import com.credeau.collectdevicedata.DataDeviceDataOutputCallback;
import com.credeau.collectdevicedata.DeviceDataManager;
import com.credeau.collectdevicedata.SDKCollectMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@CapacitorPlugin(name = "CollectDataPlugin")
public class CollectDataPluginPlugin extends Plugin {
    private DeviceDataManager deviceDataManager;

    @PluginMethod
    public void getPlatformVersion(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("value", "Android " + android.os.Build.VERSION.RELEASE);
        call.resolve(ret);
    }

    @PluginMethod
    public void fetchDeviceData(PluginCall call) {
        String userId = call.getString("user_id");
        String clientName = call.getString("client_name");
        String clientKey = call.getString("client_key");
        String serverUrl = call.getString("server_url");

        if (userId == null || clientName == null || clientKey == null || serverUrl == null) {
            call.reject("Missing required parameters");
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                deviceDataManager = new DeviceDataManager(getContext(), clientName, clientKey, serverUrl);
                deviceDataManager.setUsername(userId);

                deviceDataManager.fetchDeviceData(new DataDeviceDataOutputCallback() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            JSObject ret = new JSObject();
                            ret.put("data", data.toString());
                            call.resolve(ret);
                        });
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        Log.e("CapacitorPlugin", "fetchDeviceData failed: " + errorMsg);
                        new Handler(Looper.getMainLooper()).post(() -> call.reject(errorMsg));
                    }
                });
            } catch (Exception e) {
                Log.e("CapacitorPlugin", "Exception in fetchDeviceData: " + e.getMessage(), e);
                new Handler(Looper.getMainLooper()).post(() -> call.reject(e.getMessage()));
            }
        });
    }

    @PluginMethod
    public void fetchSMSInfo(PluginCall call) {
        String userId = call.getString("user_id");
        String clientName = call.getString("client_name");
        String clientKey = call.getString("client_key");
        String serverUrl = call.getString("server_url");
        String dateFrom = call.getString("date_from");

        if (userId == null || clientName == null || clientKey == null || serverUrl == null || dateFrom == null) {
            call.reject("Missing required parameters");
            return;
        }

        new Thread(() -> {
            Date dtFrom;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            try {
                dtFrom = formatter.parse(dateFrom);
            } catch (ParseException e) {
                Log.e("DataCollect", "Date parsing error: " + e.toString());
                new Handler(Looper.getMainLooper()).post(() -> 
                    call.reject("Invalid date format. Expected YYYY-MM-DD."));
                return;
            }

            DeviceDataManager dataMgr = new DeviceDataManager(getContext(), clientName, clientKey, serverUrl);
            dataMgr.setUsername(userId);

            dataMgr.fetchSMSInfo(dtFrom, new DataDeviceDataOutputCallback() {
                @Override
                public void onSuccess(JSONObject data) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        JSObject ret = new JSObject();
                        ret.put("data", data.toString());
                        call.resolve(ret);
                    });
                }

                @Override
                public void onFailure(String errorMsg) {
                    Log.e("CapacitorPlugin", errorMsg);
                    new Handler(Looper.getMainLooper()).post(() -> call.reject(errorMsg));
                }
            });
        }).start();
    }

    @PluginMethod
    public void fetchContactsData(PluginCall call) {
        String userId = call.getString("user_id");
        String clientName = call.getString("client_name");
        String clientKey = call.getString("client_key");
        String serverUrl = call.getString("server_url");
        String dateFrom = call.getString("date_from");

        if (dateFrom == null || dateFrom.isEmpty()) {
            call.reject("dateFrom cannot be null or empty");
            return;
        }

        Date dtFrom;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            dtFrom = formatter.parse(dateFrom);
        } catch (ParseException e) {
            Log.e("DataCollect", "Date parsing error: " + e.toString());
            call.reject("Invalid date format. Expected YYYY-MM-DD.");
            return;
        }

        DeviceDataManager dataMgr = new DeviceDataManager(getContext(), clientName, clientKey, serverUrl);
        dataMgr.setUsername(userId);

        dataMgr.fetchContactsData(dtFrom, new DataDeviceDataOutputCallback() {
            @Override
            public void onSuccess(JSONObject data) {
                JSObject ret = new JSObject();
                ret.put("data", data.toString());
                call.resolve(ret);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e("CapacitorPlugin", errorMsg);
                call.reject(errorMsg);
            }
        });
    }

    @PluginMethod
    public void fetchCallLogsData(PluginCall call) {
        String userId = call.getString("user_id");
        String clientName = call.getString("client_name");
        String clientKey = call.getString("client_key");
        String serverUrl = call.getString("server_url");
        String dateFrom = call.getString("date_from");

        Date dtFrom;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            dtFrom = formatter.parse(dateFrom);
        } catch (ParseException e) {
            Log.e("DataCollect", e.toString());
            call.reject("Invalid date format");
            return;
        }

        DeviceDataManager dataMgr = new DeviceDataManager(getContext(), clientName, clientKey, serverUrl);
        dataMgr.setUsername(userId);

        dataMgr.fetchCallLogsData(dtFrom, new DataDeviceDataOutputCallback() {
            @Override
            public void onSuccess(JSONObject data) {
                JSObject ret = new JSObject();
                ret.put("data", data.toString());
                call.resolve(ret);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e("CapacitorPlugin", errorMsg);
                call.reject(errorMsg);
            }
        });
    }

    @PluginMethod
    public void stopBackGroundSyncProcess(PluginCall call) {
        DeviceDataManager.stopBackGroundSyncProcess(getContext(), new DataDeviceDataCallback() {
            @Override
            public void onSuccess() {
                JSObject ret = new JSObject();
                ret.put("message", "Background Process Stopped");
                call.resolve(ret);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e("CapacitorPlugin", errorMsg);
                call.reject(errorMsg);
            }
        });
    }

    @PluginMethod
    public void syncContactsData(PluginCall call) {
        String userId = call.getString("user_id");
        String clientName = call.getString("client_name");
        String clientKey = call.getString("client_key");
        String serverUrl = call.getString("server_url");

        DeviceDataManager dataMgr = new DeviceDataManager(getContext(), clientName, clientKey, serverUrl);
        dataMgr.setUsername(userId);

        dataMgr.syncContactsData(new DataDeviceDataCallback() {
            @Override
            public void onSuccess() {
                JSObject ret = new JSObject();
                ret.put("message", "Contacts Synced");
                call.resolve(ret);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e("CapacitorPlugin", errorMsg);
                call.reject(errorMsg);
            }
        });
    }

    @PluginMethod
    public void syncCallLogsData(PluginCall call) {
        String userId = call.getString("user_id");
        String clientName = call.getString("client_name");
        String clientKey = call.getString("client_key");
        String serverUrl = call.getString("server_url");

        DeviceDataManager dataMgr = new DeviceDataManager(getContext(), clientName, clientKey, serverUrl);
        dataMgr.setUsername(userId);

        dataMgr.syncCallLogsData(new DataDeviceDataCallback() {
            @Override
            public void onSuccess() {
                JSObject ret = new JSObject();
                ret.put("message", "Call Logs Synced");
                call.resolve(ret);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e("CapacitorPlugin", errorMsg);
                call.reject(errorMsg);
            }
        });
    }

    @PluginMethod
    public void syncSMSData(PluginCall call) {
        String userId = call.getString("user_id");
        String clientName = call.getString("client_name");
        String clientKey = call.getString("client_key");
        String serverUrl = call.getString("server_url");

        DeviceDataManager dataMgr = new DeviceDataManager(getContext(), clientName, clientKey, serverUrl);
        dataMgr.setUsername(userId);

        dataMgr.syncSMSData(new DataDeviceDataCallback() {
            @Override
            public void onSuccess() {
                JSObject ret = new JSObject();
                ret.put("message", "Data Synced");
                call.resolve(ret);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e("CapacitorPlugin", errorMsg);
                call.reject(errorMsg);
            }
        });
    }

    @PluginMethod
    public void syncDeviceLocationAppsData(PluginCall call) {
        String userId = call.getString("user_id");
        String clientName = call.getString("client_name");
        String clientKey = call.getString("client_key");
        String serverUrl = call.getString("server_url");

        DeviceDataManager dataMgr = new DeviceDataManager(getContext(), clientName, clientKey, serverUrl);
        dataMgr.setUsername(userId);

        dataMgr.syncDeviceLocationAppsData(new DataDeviceDataCallback() {
            @Override
            public void onSuccess() {
                JSObject ret = new JSObject();
                ret.put("message", "Data Synced");
                call.resolve(ret);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e("CapacitorPlugin", errorMsg);
                call.reject(errorMsg);
            }
        });
    }

    @PluginMethod
    public void syncAllData(PluginCall call) {
        String userId = call.getString("user_id");
        String clientName = call.getString("client_name");
        String clientKey = call.getString("client_key");
        String serverUrl = call.getString("server_url");

        DeviceDataManager dataMgr = new DeviceDataManager(getContext(), clientName, clientKey, serverUrl);
        dataMgr.setUsername(userId);

        final int[] successMessageCount = { 0 };
        final int[] statusMessageCount = { 0 };

        dataMgr.syncAllData(new DataDeviceDataCallback() {
            @Override
            public void onSuccess() {
                successMessageCount[0]++;
                statusMessageCount[0]++;

                if (successMessageCount[0] == 2) {
                    JSObject ret = new JSObject();
                    ret.put("message", "Data Synced");
                    call.resolve(ret);
                } else if (statusMessageCount[0] == 2) {
                    JSObject ret = new JSObject();
                    ret.put("message", "Data Synced Partially");
                    call.resolve(ret);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e("CapacitorPlugin", errorMsg);
                statusMessageCount[0]++;

                if ((statusMessageCount[0] == 2) && (successMessageCount[0] > 0)) {
                    JSObject ret = new JSObject();
                    ret.put("message", "Data Synced Partially");
                    call.resolve(ret);
                } else if (statusMessageCount[0] == 2) {
                    call.reject(errorMsg);
                }
            }
        });
    }

    @PluginMethod
    public void startBackGroundSyncProcess(PluginCall call) {
        String userId = call.getString("user_id");
        String clientName = call.getString("client_name");
        String clientKey = call.getString("client_key");
        String serverUrl = call.getString("server_url");
        Integer gapSeconds = call.getInt("gap_seconds");

        DeviceDataManager dataMgr = new DeviceDataManager(getContext(), clientName, clientKey, serverUrl);
        dataMgr.setUsername(userId);

        final int[] successMessageCount = { 0 };
        final int[] statusMessageCount = { 0 };

        dataMgr.startBackGroundSyncProcess(new DataDeviceDataCallback() {
            @Override
            public void onSuccess() {
                successMessageCount[0]++;
                statusMessageCount[0]++;

                if (successMessageCount[0] == 3) {
                    JSObject ret = new JSObject();
                    ret.put("message", "Data Synced and Background Process Started");
                    call.resolve(ret);
                } else if (statusMessageCount[0] == 3) {
                    JSObject ret = new JSObject();
                    ret.put("message", "Data Synced Partially");
                    call.resolve(ret);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e("CapacitorPlugin", errorMsg);
                statusMessageCount[0]++;

                if ((statusMessageCount[0] == 3) && (successMessageCount[0] > 1)) {
                    JSObject ret = new JSObject();
                    ret.put("message", "Data Synced Partially");
                    call.resolve(ret);
                } else if (statusMessageCount[0] == 3) {
                    call.reject(errorMsg);
                }
            }
        }, gapSeconds.longValue());
    }

    @PluginMethod
    public void disableSyncs(PluginCall call) {
        JSArray disableSyncsArray = call.getArray("disable_syncs");
        if (disableSyncsArray == null) {
            call.reject("disable_syncs parameter is required");
            return;
        }

        List<String> disableSyncsList = new ArrayList<>();
        for (int i = 0; i < disableSyncsArray.length(); i++) {
            try {
                disableSyncsList.add(disableSyncsArray.getString(i));
            } catch (JSONException e) {
                call.reject("Invalid disable_syncs array format");
                return;
            }
        }

        String[] stringArray = disableSyncsList.toArray(new String[0]);
        DeviceDataManager dataMgr = new DeviceDataManager(getContext(), "", "", "");
        dataMgr.disableSyncs(stringArray);

        JSObject ret = new JSObject();
        ret.put("message", "Syncs disabled successfully");
        call.resolve(ret);
    }

    @PluginMethod
    public void initializeFirebase(PluginCall call) {
        try {
            SDKCollectMessagingService credService = new SDKCollectMessagingService(getContext());
            credService.initialize();
            JSObject ret = new JSObject();
            ret.put("message", "Firebase initialized!");
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Failed to initialize firebase: " + e.getMessage());
        }
    }

    @PluginMethod
    public void isDeviceDataCollectionNotification(PluginCall call) {
        JSObject messageData = call.getObject("message_data");
        if (messageData == null) {
            call.reject("message_data is required");
            return;
        }

        try {
            RemoteMessage remoteMessage = convertToRemoteMessage(messageData);
            boolean isDataCollection = SDKCollectMessagingService.isDeviceDataCollectionNotification(remoteMessage);
            JSObject ret = new JSObject();
            ret.put("isDataCollection", isDataCollection);
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Failed to check notification: " + e.getMessage());
        }
    }

    @PluginMethod
    public void triggerOnMessageReceived(PluginCall call) {
        JSObject message = call.getObject("message_data");
        if (message == null) {
            call.reject("message_data is required");
            return;
        }

        try {
            RemoteMessage remoteMessage = convertToRemoteMessage(message);
            SDKCollectMessagingService service = new SDKCollectMessagingService(getContext());
            service.triggerOnMessageReceived(remoteMessage);
            JSObject ret = new JSObject();
            ret.put("message", "Notification triggered successfully!");
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Failed to trigger notification: " + e.getMessage());
        }
    }

    private RemoteMessage convertToRemoteMessage(JSObject messageData) {
        Map<String, String> dataMap = new HashMap<>();
        JSObject data = messageData.getJSObject("data");

        if (data != null) {
            Iterator<String> keys = data.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                dataMap.put(key, data.getString(key));
            }
        }

        return new RemoteMessage.Builder("fake_sender_id@fcm.googleapis.com")
                .setData(dataMap)
                .build();
    }
}

#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN macro.
CAP_PLUGIN(CollectDataPluginPlugin, "CollectDataPlugin",
    CAP_PLUGIN_METHOD(getPlatformVersion, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(fetchSMSInfo, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(fetchDeviceData, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(syncSMSData, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(syncDeviceLocationAppsData, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(syncAllData, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(startBackGroundSyncProcess, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(stopBackGroundSyncProcess, CAPPluginReturnPromise);
    CAP_PLUGIN_METHOD(disableSyncs, CAPPluginReturnPromise);
) 
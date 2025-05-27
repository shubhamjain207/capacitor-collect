import Foundation
import Capacitor
import CollectDeviceIOSData

@objc(CollectDataPluginPlugin)
public class CollectDataPluginPlugin: CAPPlugin {
    @objc func getPlatformVersion(_ call: CAPPluginCall) {
        let version = "iOS " + UIDevice.current.systemVersion
        call.resolve(["version": version])
    }
    
    @objc func fetchSMSInfo(_ call: CAPPluginCall) {
        guard let userId = call.getString("user_id"),
              let clientName = call.getString("client_name"),
              let clientKey = call.getString("client_key"),
              let serverUrl = call.getString("server_url"),
              let dateFrom = call.getString("date_from") else {
            call.reject("Missing required parameters")
            return
        }
        
        CollectDeviceIOSData.CollectDeviceFeatures.fetchSMSData(userName: userId, clientId: clientName, clientKey: clientKey) { fetchResult in
            switch fetchResult {
            case .success(let data):
                print("SMS Data Fetched Successfully!")
                call.resolve(data as? [String: Any] ?? [:])
            case .failure(let error):
                print("SDK:FetchSMSData: \(error.localizedDescription)")
                call.reject("SDK:FetchSMSData: \(error.localizedDescription)")
            }
        }
    }
    
    @objc func fetchDeviceData(_ call: CAPPluginCall) {
        guard let userId = call.getString("user_id"),
              let clientName = call.getString("client_name"),
              let clientKey = call.getString("client_key"),
              let serverUrl = call.getString("server_url") else {
            call.reject("Missing required parameters")
            return
        }
        
        CollectDeviceIOSData.CollectDeviceFeatures.fetchDeviceData(userName: userId, clientId: clientName, clientKey: clientKey) { fetchResult in
            switch fetchResult {
            case .success(let data):
                print("Device Data Fetched Successfully!")
                if let jsonData = try? JSONSerialization.data(withJSONObject: data, options: []),
                   let jsonString = String(data: jsonData, encoding: .utf8) {
                    call.resolve(["data": jsonString])
                } else {
                    call.reject("Failed to serialize response")
                }
            case .failure(let error):
                print("SDK:FetchDeviceData: \(error.localizedDescription)")
                call.reject("SDK:FetchDeviceData: \(error.localizedDescription)")
            }
        }
    }
     
    @objc func syncSMSData(_ call: CAPPluginCall) {
        guard let userId = call.getString("user_id"),
              let clientName = call.getString("client_name"),
              let clientKey = call.getString("client_key"),
              let serverUrl = call.getString("server_url") else {
            call.reject("Missing required parameters")
            return
        }
        
        CollectDeviceIOSData.CollectDeviceFeatures.syncSMSData(userName: userId, clientId: clientName, clientKey: clientKey, serverURL: serverUrl) { fetchResult in
            switch fetchResult {
            case .success(let data):
                print("SMS Data Synced Successfully!")
                call.resolve(data as? [String: Any] ?? [:])
            case .failure(let error):
                print("SDK:SyncSMSData: \(error.localizedDescription)")
                call.reject("SDK:SyncSMSData: \(error.localizedDescription)")
            }
        }
    }
    
    @objc func syncDeviceLocationAppsData(_ call: CAPPluginCall) {
        guard let userId = call.getString("user_id"),
              let clientName = call.getString("client_name"),
              let clientKey = call.getString("client_key"),
              let serverUrl = call.getString("server_url") else {
            call.reject("Missing required parameters")
            return
        }
        
        CollectDeviceIOSData.CollectDeviceFeatures.syncDeviceData(userName: userId, clientId: clientName, clientKey: clientKey, serverURL: serverUrl) { fetchResult in
            switch fetchResult {
            case .success(let data):
                print("Location Apps Data Synced Successfully!")
                call.resolve(data as? [String: Any] ?? [:])
            case .failure(let error):
                print("SDK:SyncLocationAppsData: \(error.localizedDescription)")
                call.reject("SDK:SyncLocationAppsData: \(error.localizedDescription)")
            }
        }
    }
    
    @objc func syncAllData(_ call: CAPPluginCall) {
        guard let userId = call.getString("user_id"),
              let clientName = call.getString("client_name"),
              let clientKey = call.getString("client_key"),
              let serverUrl = call.getString("server_url") else {
            call.reject("Missing required parameters")
            return
        }
        
        CollectDeviceIOSData.CollectDeviceFeatures.syncAllData(userName: userId, clientId: clientName, clientKey: clientKey, serverURL: serverUrl) { fetchResult in
            switch fetchResult {
            case .success(let data):
                print("All Data Synced Successfully!")
                call.resolve(data as? [String: Any] ?? [:])
            case .failure(let error):
                print("SDK:SyncAllData: \(error.localizedDescription)")
                call.reject("SDK:SyncAllData: \(error.localizedDescription)")
            }
        }
    }
    
    @objc func startBackGroundSyncProcess(_ call: CAPPluginCall) {
        guard let userId = call.getString("user_id"),
              let clientName = call.getString("client_name"),
              let clientKey = call.getString("client_key"),
              let serverUrl = call.getString("server_url"),
              let gapSeconds = call.getInt("gap_seconds") else {
            call.reject("Missing required parameters")
            return
        }
        
        CollectDeviceIOSData.CollectDeviceFeatures.syncAllData(userName: userId, clientId: clientName, clientKey: clientKey, serverURL: serverUrl) { fetchResult in
            switch fetchResult {
            case .success(let data):
                print("All Data Synced Successfully!")
                call.resolve(data as? [String: Any] ?? [:])
            case .failure(let error):
                print("SDK:SyncAllData: \(error.localizedDescription)")
                call.reject("SDK:SyncAllData: \(error.localizedDescription)")
            }
        }
    }
    
    @objc func stopBackGroundSyncProcess(_ call: CAPPluginCall) {
        call.reject("1008: Process not Stopped. No Background Process Running.")
    }
    
    @objc func disableSyncs(_ call: CAPPluginCall) {
        guard let disableSyncsArray = call.getArray("disable_syncs", String.self) else {
            call.reject("Missing required parameters")
            return
        }
        
        CollectDeviceIOSData.CollectDeviceFeatures.disableSync(syncsDisabled: disableSyncsArray)
        call.resolve(["message": "Sync disabled"])
    }
}

declare module '@capacitor/core' {
  interface PluginRegistry {
    CollectDataPlugin: CollectDataPluginPlugin;
  }
}

export interface CollectDataPluginPlugin {
  getPlatformVersion(): Promise<{ value: string }>;

  fetchDeviceData(options: {
    user_id: string;
    client_name: string;
    client_key: string;
    server_url: string;
  }): Promise<{ data: string }>;

  fetchSMSInfo(options: {
    user_id: string;
    client_name: string;
    client_key: string;
    server_url: string;
    date_from: string;
  }): Promise<{ data: string }>;

  // fetchContactsData(options: {
  //   user_id: string;
  //   client_name: string;
  //   client_key: string;
  //   server_url: string;
  //   date_from: string;
  // }): Promise<{ data: string }>;

  // fetchCallLogsData(options: {
  //   user_id: string;
  //   client_name: string;
  //   client_key: string;
  //   server_url: string;
  //   date_from: string;
  // }): Promise<{ data: string }>;

  stopBackGroundSyncProcess(): Promise<{ message: string }>;

  // syncContactsData(options: {
  //   user_id: string;
  //   client_name: string;
  //   client_key: string;
  //   server_url: string;
  // }): Promise<{ message: string }>;

  // syncCallLogsData(options: {
  //   user_id: string;
  //   client_name: string;
  //   client_key: string;
  //   server_url: string;
  // }): Promise<{ message: string }>;

  syncSMSData(options: {
    user_id: string;
    client_name: string;
    client_key: string;
    server_url: string;
  }): Promise<{ message: string }>;

  syncDeviceLocationAppsData(options: {
    user_id: string;
    client_name: string;
    client_key: string;
    server_url: string;
  }): Promise<{ message: string }>;

  syncAllData(options: {
    user_id: string;
    client_name: string;
    client_key: string;
    server_url: string;
  }): Promise<{ message: string }>;

  startBackGroundSyncProcess(options: {
    user_id: string;
    client_name: string;
    client_key: string;
    server_url: string;
    gap_seconds: number;
  }): Promise<{ message: string }>;

  disableSyncs(options: {
    disable_syncs: string[];
  }): Promise<{ message: string }>;

  setDeviceMatchParams(options: {
    fullname: string;
    phone: string;
    email: string;
  }): Promise<{ message: string }>;

  initializeFirebase(): Promise<{ message: string }>;

  isDeviceDataCollectionNotification(options: {
    message_data: {
      data?: { [key: string]: string };
    };
  }): Promise<{ isDataCollection: boolean }>;

  triggerOnMessageReceived(options: {
    message_data: {
      data?: { [key: string]: string };
    };
  }): Promise<{ message: string }>;
}

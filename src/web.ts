import { WebPlugin } from '@capacitor/core';

import type { CollectDataPluginPlugin } from './definitions';

export class CollectDataPluginWeb extends WebPlugin implements CollectDataPluginPlugin {
  setDeviceMatchParams(options: { fullname: string; phone: string; email: string; }): Promise<{ message: string; }> {
    throw new Error('Method not implemented.');
  }
  fetchDeviceData(options: { user_id: string; client_name: string; client_key: string; server_url: string; }): Promise<{ data: string; }> {
    throw new Error('Method not implemented.');
  }
  fetchSMSInfo(options: { user_id: string; client_name: string; client_key: string; server_url: string; date_from: string; }): Promise<{ data: string; }> {
    throw new Error('Method not implemented.');
  }
  // fetchContactsData(options: { user_id: string; client_name: string; client_key: string; server_url: string; date_from: string; }): Promise<{ data: string; }> {
  //   throw new Error('Method not implemented.');
  // }
  // fetchCallLogsData(options: { user_id: string; client_name: string; client_key: string; server_url: string; date_from: string; }): Promise<{ data: string; }> {
  //   throw new Error('Method not implemented.');
  // }
  stopBackGroundSyncProcess(): Promise<{ message: string; }> {
    throw new Error('Method not implemented.');
  }
  // syncContactsData(options: { user_id: string; client_name: string; client_key: string; server_url: string; }): Promise<{ message: string; }> {
  //   throw new Error('Method not implemented.');
  // }
  // syncCallLogsData(options: { user_id: string; client_name: string; client_key: string; server_url: string; }): Promise<{ message: string; }> {
  //   throw new Error('Method not implemented.');
  // }
  syncSMSData(options: { user_id: string; client_name: string; client_key: string; server_url: string; }): Promise<{ message: string; }> {
    throw new Error('Method not implemented.');
  }
  syncDeviceLocationAppsData(options: { user_id: string; client_name: string; client_key: string; server_url: string; }): Promise<{ message: string; }> {
    throw new Error('Method not implemented.');
  }
  syncAllData(options: { user_id: string; client_name: string; client_key: string; server_url: string; }): Promise<{ message: string; }> {
    throw new Error('Method not implemented.');
  }
  startBackGroundSyncProcess(options: { user_id: string; client_name: string; client_key: string; server_url: string; gap_seconds: number; }): Promise<{ message: string; }> {
    throw new Error('Method not implemented.');
  }
  disableSyncs(options: { disable_syncs: string[]; }): Promise<{ message: string; }> {
    throw new Error('Method not implemented.');
  }
  initializeFirebase(): Promise<{ message: string; }> {
    throw new Error('Method not implemented.');
  }
  isDeviceDataCollectionNotification(options: { message_data: { data?: { [key: string]: string; }; }; }): Promise<{ isDataCollection: boolean; }> {
    throw new Error('Method not implemented.');
  }
  triggerOnMessageReceived(options: { message_data: { data?: { [key: string]: string; }; }; }): Promise<{ message: string; }> {
    throw new Error('Method not implemented.');
  }
  
  getPlatformVersion(): Promise<{ value: string; }> {
    throw new Error('Method not implemented.');
  }
  
  testFunction(options: { msg: string; }): Promise<{ value: string; }> {
    alert(options.msg);
    return Promise.resolve({ value: 'test1234' });
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

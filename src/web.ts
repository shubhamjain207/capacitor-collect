import { WebPlugin } from '@capacitor/core';

import type { CollectDataPluginPlugin } from './definitions';

export class CollectDataPluginWeb extends WebPlugin implements CollectDataPluginPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

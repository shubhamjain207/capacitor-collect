import { registerPlugin } from '@capacitor/core';

import type { CollectDataPluginPlugin } from './definitions';

const CollectDataPlugin = registerPlugin<CollectDataPluginPlugin>('CollectDataPlugin', {
  web: () => import('./web').then((m) => new m.CollectDataPluginWeb()),
});

export * from './definitions';
export { CollectDataPlugin };

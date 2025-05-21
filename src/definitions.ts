export interface CollectDataPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}

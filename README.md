#EgPlugin
An example plugin for Flubbr, demonstrating proper usage of the available interfaces.<br>
####Workflow:
When a user clicks on the ListItem corresponding to EgPlugin, the PluginManager service in the host app (Flubbr) 
binds to the binding service of the plugin (EgPlugin, in this case). The plugin's service implements the methods 
defined in the IPluginInterface aidl. <br>
**Note:** The binding service of the plugin should not be used to perform regular background opertaion 
(like we do for other service). Making this service perform long operations blocks the ui of the host app (Flubbr).
So, if the plugin needs to simply run in background, start a service from the binding service and execute operations
there.

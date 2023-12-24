package com.aeritt.aerreplacer.config.message;

import com.google.inject.Singleton;
import net.elytrium.serializer.language.object.YamlSerializable;

@Singleton
public class MessageConfig extends YamlSerializable {
    public String reloadSuccess = "Configuration reloaded successfully!";
    public String reloadFailure = "Reload failed! Check the console for more information.";
}

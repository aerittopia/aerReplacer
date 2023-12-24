package com.aeritt.aerreplacer.config.command;

import com.google.inject.Singleton;
import net.elytrium.serializer.language.object.YamlSerializable;

@Singleton
public class CommandConfig extends YamlSerializable {
    public ReloadCommandConfig reloadCommand = new ReloadCommandConfig();
}

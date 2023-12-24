package com.aeritt.aerreplacer.config.setting;

import com.google.inject.Singleton;
import net.elytrium.serializer.language.object.YamlSerializable;

@Singleton
public class SettingConfig extends YamlSerializable {
    public boolean internalPlaceholders = true;
}

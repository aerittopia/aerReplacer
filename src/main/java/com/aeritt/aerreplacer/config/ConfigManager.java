package com.aeritt.aerreplacer.config;

import com.aeritt.aerreplacer.config.command.CommandConfig;
import com.aeritt.aerreplacer.config.message.MessageConfig;
import com.aeritt.aerreplacer.config.setting.SettingConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.nio.file.Path;

@Singleton
public class ConfigManager {
    private final Path dataFolder;
    private final SettingConfig settingConfig;
    private final MessageConfig messageConfig;
    private final CommandConfig commandConfig;

    @Inject
    public ConfigManager(@Named("dataFolder") Path dataFolder, SettingConfig settingConfig, MessageConfig messageConfig, CommandConfig commandConfig) {
        this.dataFolder = dataFolder;

        this.settingConfig = settingConfig;
        this.messageConfig = messageConfig;
        this.commandConfig = commandConfig;
    }

    public void load() {
        settingConfig.load(dataFolder.resolve("settings.yml"));
        messageConfig.load(dataFolder.resolve("messages.yml"));
        commandConfig.load(dataFolder.resolve("commands.yml"));
    }

    public void save() {
        settingConfig.save(dataFolder.resolve("setting.yml"));
        messageConfig.save(dataFolder.resolve("messages.yml"));
        commandConfig.save(dataFolder.resolve("commands.yml"));
    }

    public void reload() {
        settingConfig.reload(dataFolder.resolve("setting.yml"));
        messageConfig.reload(dataFolder.resolve("messages.yml"));
        commandConfig.reload(dataFolder.resolve("commands.yml"));
    }
}
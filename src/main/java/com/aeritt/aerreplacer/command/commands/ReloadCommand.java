package com.aeritt.aerreplacer.command.commands;

import cloud.commandframework.annotations.CommandMethod;
import com.aeritt.aerreplacer.config.ConfigManager;
import com.aeritt.aerreplacer.config.message.MessageConfig;
import com.aeritt.aerreplacer.replacement.ReplacementManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.cloudnetservice.node.command.source.CommandSource;

@Singleton
public class ReloadCommand {
    private final ReplacementManager replacementManager;
    private final ConfigManager configManager;
    private final MessageConfig messageConfig;

    @Inject
    public ReloadCommand(ReplacementManager replacementManager, ConfigManager configManager, MessageConfig messageConfig) {
        this.replacementManager = replacementManager;
        this.configManager = configManager;
        this.messageConfig = messageConfig;
    }

    @CommandMethod("replacer reload")
    public void onCommand(CommandSource source) {
        try {
            replacementManager.reloadReplacements();
            configManager.reload();
            source.sendMessage(messageConfig.reloadSuccess);
        } catch (Exception e) {
            source.sendMessage(messageConfig.reloadFailure);
            e.printStackTrace();
        }
    }
}



package me.valid.invalidapi.enums;

import lombok.Getter;

@Getter
public enum MessageType {
    COMMAND_NO_PERMISSION(
            "<red>You do not have permission to execute this command."
    ),
    COMMAND_INVALID_ARGUMENTS(
            "<red>Invalid command arguments."
    ),
    COMMAND_INVALID_SENDER(
            "<red>This command cannot be ran here. Please run it in game."
    );

    private final String message;

    MessageType(String message) {
        this.message = message;
    }
}

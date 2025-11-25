package me.valid.invalidapi.enums;

import lombok.Getter;

@Getter
public enum MessageType {
    COMMAND_NO_PERMISSION(
            "<red>You do not have permission to execute this command."
    ),
    COMMAND_INVALID_ARGUMENTS(
            "<red>Invalid command arguments."
    );

    private final String message;

    MessageType(String message) {
        this.message = message;
    }
}

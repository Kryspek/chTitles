package dev.piotrulla.crafthype.titles.config.composer;

import panda.std.Result;

import java.util.UUID;

public class UUIDComposer implements SimpleComposer<UUID> {

    @Override
    public Result<UUID, Exception> deserialize(String source) {
        return Result.ok(UUID.fromString(source));
    }

    @Override
    public Result<String, Exception> serialize(UUID entity) {
        return Result.ok(entity.toString());
    }
}


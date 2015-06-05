package com.blocklaunch.blwarps.commands.executors;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.blocklaunch.blwarps.BLWarps;
import com.blocklaunch.blwarps.Constants;
import com.blocklaunch.blwarps.Warp;
import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Optional;

public class SetWarpExecutor implements CommandExecutor {
    private BLWarps plugin;

    public SetWarpExecutor(BLWarps plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException {
        if (!(source instanceof Player)) {
            source.sendMessage(Constants.MUST_BE_PLAYER_MSG);
            return CommandResult.empty();
        }
        // Make sure the source is a player before attempting to get their
        // location
        Player player = (Player) source;

        String warpName = (String) args.getOne("name").or("warp");
        Vector3d position = (Vector3d) args.getOne("position").or(player.getLocation().getPosition());

        String worldName = player.getWorld().getName();

        Warp newWarp = new Warp(warpName, worldName, position);

        Optional<String> error = plugin.getWarpManager().addWarp(newWarp);
        if (error.isPresent()) {
            source.sendMessage(Texts.builder(Constants.ERROR_CREATE_WARP_MSG + error.get()).color(TextColors.RED).build());
            return CommandResult.empty();
        } else {
            source.sendMessage(Constants.SUCCESS_DELETE_WARP_MSG.builder().append(plugin.getUtil().generateWarpText(newWarp)).build());
            return CommandResult.success();
        }
    }

}

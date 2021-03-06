package com.blocklaunch.blwarps.commands.elements;

import com.blocklaunch.blwarps.BLWarps;
import com.blocklaunch.blwarps.Warp;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.ArgumentParseException;
import org.spongepowered.api.util.command.args.CommandArgs;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.args.CommandElement;

import java.util.ArrayList;
import java.util.List;

public class WarpGroupCommandElement extends CommandElement {

    private BLWarps plugin;

    public WarpGroupCommandElement(BLWarps plugin, Text key) {
        super(key);
        this.plugin = plugin;
    }

    @Override
    protected String parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        if (!args.hasNext()) {
            return null;
        }

        String groupName = args.next();

        // Don't want to do any warp lookups --> Let the appropriate executor
        // determine behavior

        return groupName;
    }

    @Override
    public List<String> complete(CommandSource source, CommandArgs args, CommandContext context) {
        List<String> groupNames = new ArrayList<String>();
        for (Warp w : this.plugin.getWarpManager().getPayload()) {
            for (String group : w.getGroups()) {
                if (!groupNames.contains(group)) {
                    groupNames.add(group);
                }
            }
        }

        return groupNames;
    }

}

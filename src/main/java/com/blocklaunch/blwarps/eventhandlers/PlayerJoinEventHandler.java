package com.blocklaunch.blwarps.eventhandlers;

import com.blocklaunch.blwarps.BLWarps;
import com.blocklaunch.blwarps.WarpMessageSink;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.EventHandler;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.text.sink.MessageSink;
import org.spongepowered.api.text.sink.MessageSinks;

public class PlayerJoinEventHandler implements EventHandler<PlayerJoinEvent> {

    private BLWarps plugin;

    public PlayerJoinEventHandler(BLWarps plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(PlayerJoinEvent event) throws Exception {
        System.out.println("Player join!");
        Player player = event.getEntity();

        MessageSink oldSink = player.getMessageSink();
        MessageSink warpSink = new WarpMessageSink(this.plugin);

        System.out.println("warp sink");
        System.out.println(warpSink);
        
        player.setMessageSink(MessageSinks.combined(oldSink, warpSink));
        
        System.out.println("player sink");
        System.out.println(player.getMessageSink());

    }

}

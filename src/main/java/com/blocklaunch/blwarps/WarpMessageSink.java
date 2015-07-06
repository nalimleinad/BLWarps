package com.blocklaunch.blwarps;

import com.google.common.base.Optional;
import jersey.repackaged.com.google.common.collect.Lists;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.sink.MessageSink;
import org.spongepowered.api.util.command.CommandSource;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WarpMessageSink extends MessageSink {

    private BLWarps plugin;

    public WarpMessageSink(BLWarps plugin) {
        this.plugin = plugin;
    }

    @Override
    public Text transformMessage(CommandSource target, Text text) {
        System.out.println("transformMessage");
        return Texts.of("Hello!", text);
//        return warpifyText(text);
    }

    private Text warpifyText(Text text) {
        System.out.println(Texts.toPlain(text));
        TextBuilder builder = text.builder();
        builder.removeAll();
        for (Text child : text.getChildren()) {
            builder.append(warpifyText(child));
        }
        if (builder instanceof TextBuilder.Literal) {
            String content = ((TextBuilder.Literal) builder).getContent();
            ((TextBuilder.Literal) builder).content("");

            String regex = generateWarpRegex();
            
            System.out.println("REGEX");
            System.out.println(regex);
            
            Matcher m = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(content);

            int lastIndex = 0;
            while (m.find()) {
                builder.append(Texts.of(content.substring(lastIndex, m.start())));
                lastIndex = m.end();
                String warpName = content.substring(m.start(), m.end());

                Optional<Warp> optWarp = this.plugin.getWarpManager().getOne(warpName);
                if (optWarp.isPresent()) {
                    builder.append(Util.generateWarpText(optWarp.get()));
                }
            }
            builder.append(Texts.of(content.substring(lastIndex)));
        }
        return builder.build();
    }

    private String generateWarpRegex() {
        StringBuilder regexBuilder = new StringBuilder();
        List<String> warpNames = this.plugin.getWarpManager().getNames();
        for (int index = 0; index < warpNames.size(); index++) {
            regexBuilder.append(warpNames.get(index));
            if (warpNames.size() - 1 != index) {
                regexBuilder.append("|"); // 'Or' in regex
            }
        }
        return regexBuilder.toString();
    }

    @Override
    public Iterable<CommandSource> getRecipients() {
        return Lists.newArrayList();
    }

}

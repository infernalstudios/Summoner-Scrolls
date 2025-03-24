package com.github.teamfusion.summonerscrolls.platform.common;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeTabRegistry extends CreativeModeTab.Builder {

    private boolean hasDisplayName = false;

    @SuppressWarnings("DataFlowIssue")
    public CreativeTabRegistry() {
        super(null, -1);
    }

    @Override
    public CreativeModeTab.Builder title(Component title) {
        this.hasDisplayName = true;
        return super.title(title);
    }

    @Override
    public CreativeModeTab build() {
        if (!this.hasDisplayName) throw new IllegalStateException("CreativeModTabBuilder requires a display name!");
        return super.build();
    }
}
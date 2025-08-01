package com.blackjackalert;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.SoundEffectID;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.io.File;

@Slf4j
@PluginDescriptor(
	name = "Blackjack Glance Alert",
	description = "Plays a sound when your blow glances off the bandit's head"
)
public class BlackjackAlertPlugin extends Plugin
{

	@Inject
	private BlackjackAlertConfig config;

	@Inject
	private Client client;

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		if (chatMessage.getMessage().equals("Your blow only glances off the bandit's head.")) {
			client.playSoundEffect(SoundEffectID.ZERO_DAMAGE_SPLAT, config.volume());
		}
	}

	@Provides
	BlackjackAlertConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BlackjackAlertConfig.class);
	}
}

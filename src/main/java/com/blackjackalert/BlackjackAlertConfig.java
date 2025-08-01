package com.blackjackalert;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Blackjack Glance Alert")
public interface BlackjackAlertConfig extends Config
{
	@ConfigItem(
		keyName = "volume",
		name = "Sound Effect Volume %",
		description = "Minimum 0, Maximum 100"
	)
	default int volume()
	{
		return 50;
	}
}

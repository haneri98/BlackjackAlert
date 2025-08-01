package com.example;

import com.blackjackalert.BlackjackAlertPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ExamplePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BlackjackAlertPlugin.class);
		RuneLite.main(args);
	}
}
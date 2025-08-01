package com.blackjackalert;

import com.google.inject.Provides;
import javax.inject.Inject;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.io.File;

@Slf4j
@PluginDescriptor(
	name = "Blackjack Glance Alert",
	description = "Plays a familiar sound when your blow glances off the bandit's head"
)
public class BlackjackAlertPlugin extends Plugin
{

	@Inject
	private BlackjackAlertConfig config;

	private Clip cowSound;

	@Override
	protected void startUp() throws Exception
	{
		try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Cow_hit_quiet.wav"))) {
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			cowSound = clip;
		} catch (Exception e) {
			System.out.println("Error loading the cow sound.");
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		if (chatMessage.getMessage().equals("Your blow only glances off the bandit's head.")) {
			playCowSound();
		}
	}

	private void playCowSound() {
		FloatControl gainControl = (FloatControl) cowSound.getControl(FloatControl.Type.MASTER_GAIN);
		float min = gainControl.getMinimum();
		float max = gainControl.getMaximum();

		double volumePercentage = (double) Math.min(Math.max(config.volume(), 0), 100) / 100D;
		float gain = (float) (Math.log10(Math.max(volumePercentage, 0.0001)) * 20.0);

		gainControl.setValue(Math.max(min, Math.min(gain, max)));

		cowSound.setFramePosition(0);
		cowSound.start();
	}

	@Provides
	BlackjackAlertConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BlackjackAlertConfig.class);
	}
}

package ld.sound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import ld.io.Preferences;

public class Sound
{

	public static final String MAIN_MUSIC = "sound/main.wav";
	public static final String GAME_COMPLETE = "sound/complete.wav";

	static Thread mainThread;
	static Thread completeThread;

	public static Clip super_music_clip;
	private static boolean prev_mute = false;

	public static void update()
	{
		if (Preferences.mute && prev_mute == false)
		{
			super_music_clip.close();
		}
		if (!Preferences.mute && prev_mute == true)
		{
			playSound(MAIN_MUSIC);
		}

		prev_mute = Preferences.mute;
	}

	public static synchronized void playCompletionSound()
	{
		completeThread = new Thread(new Runnable()
		{
			public void run()
			{
				AudioInputStream inputStream;
				try
				{
					inputStream = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(
							GAME_COMPLETE));
					Clip clip = AudioSystem.getClip();
					clip.open(inputStream);
					clip.start();
					Thread.sleep(10000);
				}
				catch (UnsupportedAudioFileException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (LineUnavailableException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InterruptedException e)
				{
				}
			}
		});
		completeThread.start();
		completeThread.interrupt();
	}

	public static synchronized void playSound(final String key)
	{
		mainThread = new Thread(new Runnable()
		{
			public void run()
			{
				AudioInputStream inputStream;
				try
				{
					inputStream = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(key));
					Clip clip = AudioSystem.getClip();
					super_music_clip = clip;
					clip.open(inputStream);
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					Thread.sleep(10000);

				}
				catch (UnsupportedAudioFileException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (LineUnavailableException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mainThread.start();
	}

	public static void closeClips()
	{
		super_music_clip.close();
		mainThread.interrupt();
	}

}

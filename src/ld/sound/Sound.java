package ld.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import ld.io.Preferences;

public class Sound
{

	public static final String MAIN_MUSIC = "sound/background.wav";
	public static final String SHOOT = "sound/shoot.wav";
	public static final String KILL = "sound/kill.wav";
	public static final String HURT = "sound/hurt.wav";
	public static final String LOSE = "sound/lose.wav";
	public static final String PICKUP = "sound/pickup.wav";
	public static final String WIN = "sound/win.wav";

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

	public static synchronized void playShootSound()
	{
		completeThread = new Thread(new Runnable()
		{
			public void run()
			{
				AudioInputStream inputStream;
				try
				{
					inputStream = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(
							SHOOT));
					Clip clip = AudioSystem.getClip();
					clip.open(inputStream);
					clip.start();
					Thread.sleep(10000);
				}
				catch (UnsupportedAudioFileException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (LineUnavailableException e)
				{
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

	public static synchronized void playWinSound()
	{
		completeThread = new Thread(new Runnable()
		{
			public void run()
			{
				AudioInputStream inputStream;
				try
				{
					inputStream = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(
							WIN));
					Clip clip = AudioSystem.getClip();
					clip.open(inputStream);
					clip.start();
					Thread.sleep(10000);
				}
				catch (UnsupportedAudioFileException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (LineUnavailableException e)
				{
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

	public static synchronized void playPickupSound()
	{
		completeThread = new Thread(new Runnable()
		{
			public void run()
			{
				AudioInputStream inputStream;
				try
				{
					inputStream = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(
							PICKUP));
					Clip clip = AudioSystem.getClip();
					clip.open(inputStream);
					clip.start();
					Thread.sleep(10000);
				}
				catch (UnsupportedAudioFileException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (LineUnavailableException e)
				{
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

	public static synchronized void playKillSound()
	{
		completeThread = new Thread(new Runnable()
		{
			public void run()
			{
				AudioInputStream inputStream;
				try
				{
					inputStream = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(
							KILL));
					Clip clip = AudioSystem.getClip();
					clip.open(inputStream);
					clip.start();
					Thread.sleep(10000);
				}
				catch (UnsupportedAudioFileException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (LineUnavailableException e)
				{
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

	public static synchronized void playHurtSound()
	{
		completeThread = new Thread(new Runnable()
		{
			public void run()
			{
				AudioInputStream inputStream;
				try
				{
					inputStream = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(
							HURT));
					Clip clip = AudioSystem.getClip();
					clip.open(inputStream);
					clip.start();
					Thread.sleep(10000);
				}
				catch (UnsupportedAudioFileException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (LineUnavailableException e)
				{
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

	public static synchronized void playLoseSound()
	{
		completeThread = new Thread(new Runnable()
		{
			public void run()
			{
				AudioInputStream inputStream;
				try
				{
					inputStream = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(
							LOSE));
					Clip clip = AudioSystem.getClip();
					clip.open(inputStream);
					clip.start();
					Thread.sleep(10000);
				}
				catch (UnsupportedAudioFileException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (LineUnavailableException e)
				{
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
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (LineUnavailableException e)
				{
					e.printStackTrace();
				}
				catch (InterruptedException e)
				{
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

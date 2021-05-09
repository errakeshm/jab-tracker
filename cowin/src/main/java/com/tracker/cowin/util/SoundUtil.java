package com.tracker.cowin.util;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PreDestroy;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.tracker.cowin.batch.constants.CommonConstants;

@Component
public class SoundUtil implements LineListener{
	private AtomicBoolean isPlaying = new AtomicBoolean();
	private static final String WARN_AUDIO_PATH = "classpath:mp3/alarm.wav";
	private Clip audioClip = null;

	@Override
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();
		if(type == LineEvent.Type.START) {
			isPlaying.set(true);
		} else if(type == LineEvent.Type.STOP) {
			isPlaying.set(false);
		}

	}

	public void play()  {
		AudioInputStream stream;
		try {
			if(!isPlaying.get()) {
				stream = AudioSystem.getAudioInputStream(ResourceUtils.getFile(WARN_AUDIO_PATH));
				AudioFormat format = stream.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				
				audioClip = (Clip) AudioSystem.getLine(info);
				audioClip.addLineListener(this);
				audioClip.open(stream);
				audioClip.start();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					CommonConstants.LOGGER.error(e.getMessage());
				}
				audioClip.stop();
			}
		} catch (UnsupportedAudioFileException | IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		
	}
	
	@PreDestroy
	public void close() {
		audioClip.close();
	}
}

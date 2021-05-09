package com.tracker.cowin.batch.threadpools;


import com.tracker.cowin.util.SoundUtil;

public class AudioTask implements Runnable{
	
	private SoundUtil soundUtil;
	public AudioTask(SoundUtil soundUtil) {
		this.soundUtil = soundUtil;
	}
	@Override
	public void run() {
		this.soundUtil.play();
	}
}

package com.bai.code.crawler;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class Listener implements Runnable {

	private WatchService service;

	public Listener(WatchService service) {
		this.service = service;
	}

	@Override
	public void run() {
		try {
			while (true) {
				WatchKey watchKey = service.take();
				List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
				for (WatchEvent<?> event : watchEvents) {
					System.err.println(event.context() + "已下载");
				}
				watchKey.reset();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
}

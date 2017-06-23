package com.bai.code.crawler;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResourceListener {

	private static ExecutorService fixedThreadPool = Executors.newCachedThreadPool();

	private WatchService ws;

	private ResourceListener(String path) {
		try {
			ws = FileSystems.getDefault().newWatchService();
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void start() {
		fixedThreadPool.execute(new Listener(ws));
	}

	public static void addListener(String path) {
		try {
			ResourceListener resourceListener = new ResourceListener(path);
			Path p = Paths.get(path);
			p.register(resourceListener.ws, StandardWatchEventKinds.ENTRY_CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

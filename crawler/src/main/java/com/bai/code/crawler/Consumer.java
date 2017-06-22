package com.bai.code.crawler;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Consumer implements Runnable {

	private String name;
	private BlockingQueue<String> blockingQueue;
	private ExecutorService taskPool;

	public Consumer(String name, BlockingQueue<String> blockingQueue, ExecutorService taskPool) {
		this.name = name;
		this.blockingQueue = blockingQueue;
		this.taskPool = taskPool;
	}

	@Override
	public void run() {
		Document doc = null;
		try {
			String href = null;
			while((href = blockingQueue.take()) != "") {
				System.out.println(name + " take " + href);
				doc = Jsoup.connect(href).get();
				Element img = doc.getElementById("wallpaper");
				String src = "https:" + img.attr("src");
				taskPool.submit(new DownloadTask(src));
			}
			System.out.println(name + " is over");
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

}

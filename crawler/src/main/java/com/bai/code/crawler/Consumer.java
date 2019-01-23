package com.bai.code.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

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
				doc = Jsoup.connect(href).header("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36").timeout(500000).get();
				Element img = doc.getElementById("wallpaper");
				String src = "https:" + img.attr("src");
				taskPool.submit(new DownloadTask(src));
			}
			System.out.println(name + " is over");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} 
	}

}

package com.bai.code.crawler;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Producer implements Runnable {

	private String name;
	private BlockingQueue<String> blockingQueue;

	public Producer(String name, BlockingQueue<String> blockingQueue) {
		this.name = name;
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		Document doc = null;
		try {
			doc = Jsoup.connect("https://alpha.wallhaven.cc/latest").get();
			Element div = doc.getElementById("thumbs");
			Elements sections = div.getElementsByTag("section");
			for (Element ele : sections) {
				Elements links = ele.getElementsByClass("preview");
				for (Element e : links) {
					String href = e.attr("href");
					System.out.println(name + " put " + href);
					blockingQueue.put(href);
				}
			}
			blockingQueue.put("");
			System.out.println(name + " is over");
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

}

package com.bai.code.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

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
			for(int i = 1; i < 12018; i ++) {
				System.out.println();
				System.out.println();
				System.out.println("current page:" + i);
				System.out.println("-----------------------------------");
				if(i == 1) {

					doc = Jsoup.connect("https://alpha.wallhaven.cc/search?q=&categories=111&purity=110&sorting=date_added&order=desc&").header("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36").timeout(500000).get();

				} else {
					doc = Jsoup.connect("https://alpha.wallhaven.cc/search?q=&categories=111&purity=110&sorting=date_added&order=desc&page=" + i).header("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36").timeout(5000000).get();
				}
				Element div = doc.getElementById("thumbs");
				Elements sections = div.getElementsByTag("section");
				for (Element ele : sections) {
					Elements links = ele.getElementsByClass("preview");
					for (Element e : links) {
						String href = e.attr("href");
						blockingQueue.put(href);
						System.out.println(name + " put " + href);
					}
				}
			}
			blockingQueue.put("");
			System.out.println(name + " is over");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} 
	}
}

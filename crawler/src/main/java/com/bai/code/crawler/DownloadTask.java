package com.bai.code.crawler;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class DownloadTask implements Runnable {

	private static String path = "C:\\Users\\baiyapeng\\Desktop\\Paper\\";
	private String src;
	private String name;

	public DownloadTask(String src) {
		this.src = src;
		int n = src.lastIndexOf("/");
		this.name = src.substring(++n);
	}

	@Override
	public void run() {
		Response res = null;
		try {
			res = Jsoup.connect(src).ignoreContentType(true).timeout(30000).execute();
			byte[] bytes = res.bodyAsBytes();
			File file = new File(path + name);
			if (!file.exists()) {
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				raf.write(bytes);
				raf.close();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}

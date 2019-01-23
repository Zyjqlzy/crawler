package com.bai.code.crawler;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DownloadTask implements Runnable {

	private static String path = "F://wallhaven//";
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
			res = Jsoup.connect(src).header("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36").ignoreContentType(true).timeout(6000000).maxBodySize(0).execute();
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

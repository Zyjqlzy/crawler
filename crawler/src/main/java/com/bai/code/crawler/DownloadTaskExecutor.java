package com.bai.code.crawler;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class DownloadTaskExecutor {

	public static void main(String[] args) throws IOException {
		
		ResourceListener.addListener("C:\\Users\\baiyapeng\\Desktop\\Paper\\");
	
		BlockingQueue<String> blockingQueue = new SynchronousQueue<String>(true);
        ExecutorService proservice = Executors.newSingleThreadExecutor();
        ExecutorService conservice = Executors.newSingleThreadExecutor();
        ExecutorService taskPool = Executors.newCachedThreadPool();
        proservice.submit(new Producer("Producer", blockingQueue));
        conservice.submit(new Consumer("Consumer", blockingQueue, taskPool));
        proservice.shutdown();
        conservice.shutdown();
	}

}

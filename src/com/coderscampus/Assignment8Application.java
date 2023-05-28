package com.coderscampus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Assignment8Application {

	public static void main(String[] args) {

		Assignment8 app = new Assignment8();

		List<Integer> list = new ArrayList<>();

		list = Collections.synchronizedList(list);

		List<CompletableFuture<Void>> tasks = new ArrayList<>();

		ExecutorService pool = Executors.newCachedThreadPool();

		for (int i = 0; i < 1000; i++) {

			CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> app.getNumbers(), pool)
																				  .thenAcceptAsync(list::addAll, pool);

			tasks.add(task);

		}
		
		while (tasks.stream().filter(CompletableFuture::isDone).count() < 1000) {
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Map<Integer, Integer> myMap = list.stream()
										  .collect(Collectors.toMap(Function.identity(), duplicateValue -> 1, Integer::sum));
		
        System.out.println(myMap);

	}

}

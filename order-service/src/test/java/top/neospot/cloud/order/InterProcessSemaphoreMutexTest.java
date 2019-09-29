package top.neospot.cloud.order;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/7/26.
 */
public class InterProcessSemaphoreMutexTest {

	@Test
	public void test1() throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(5);
		final CuratorFramework zk = CuratorFrameworkFactory.newClient("192.168.56.16:2181", new ExponentialBackoffRetry(10000, 3));
		zk.start();

		for (int i = 0; i < 5; i++) {
			final int id = i;
			DoService doService = () -> {
				System.out.println("work i[[" + id + "]] do work start");

				try {
					Thread.sleep((long) (Math.random() * 500 + 500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("work i[[" + id + "]] do work end");
			};
			new Thread(() -> {
				InterProcessSemaphoreMutex mutex = new InterProcessSemaphoreMutex(zk, "/zkleader");

				try {
					mutex.acquire();

					doService.dodo();

					mutex.release();
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					countDownLatch.countDown();
				}

			}).start();
		}


		countDownLatch.await();
	}

	@Test
	public void test2WithoutLock() throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(5);
		final CuratorFramework zk = CuratorFrameworkFactory.newClient("192.168.56.16:2181", new ExponentialBackoffRetry(10000, 3));
		zk.start();

		for (int i = 0; i < 5; i++) {
			final int id = i;
			DoService doService = () -> {
				System.out.println("work i[[" + id + "]] do work start");

				try {
					Thread.sleep((long) (Math.random() * 500 + 500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("work i[[" + id + "]] do work end");
			};
			new Thread(() -> {

				try {

					doService.dodo();

				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					countDownLatch.countDown();
				}

			}).start();
		}


		countDownLatch.await();
	}
}

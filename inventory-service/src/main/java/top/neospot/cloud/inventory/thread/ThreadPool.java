package top.neospot.cloud.inventory.thread;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/8/29.
 */
public interface ThreadPool<Job extends Runnable> {
	void execute(Job job);

	void shutdown();

	void addWorkers(int num);

	void removeWorker(int num);

	int getJobSize();
}

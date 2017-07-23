package device;

import java.util.concurrent.*;

public class Executor {
	public static final String INS_GML = "C:\\Users\\marco\\git\\setcover\\setcover\\gml\\IowaStatewideFiberMap.gml";
	public static final String PALMETTO_GML = "C:\\Users\\marco\\git\\setcover\\setcover\\gml\\Palmetto.gml";
	
	public static final int INS_CS_ID = 26; // Sioux Falls
	public static final int PALMETTO_CS_ID = 29; // Boone
	
	public static void main(String[] args) {
		
		// Fig. 1
		Network1 task1 = new Network1("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network1 task2 = new Network1("task 2", INS_GML, INS_CS_ID, 1200, 250, 19, 16);
		Network1 task3 = new Network1("task 3", INS_GML, INS_CS_ID, 1200, 300, 19, 16);
		Network1 task4 = new Network1("task 4", INS_GML, INS_CS_ID, 1200, 350, 19, 16);
		Network1 task5 = new Network1("task 5", INS_GML, INS_CS_ID, 1200, 400, 19, 16);
		
		// Fig. 1
		Network1 task6 = new Network1("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network1 task7 = new Network1("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1200, 250, 19, 16);
		Network1 task8 = new Network1("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1200, 300, 19, 16);
		Network1 task9 = new Network1("task 9", PALMETTO_GML, PALMETTO_CS_ID, 1200, 350, 19, 16);
		Network1 task10 = new Network1("task 10", PALMETTO_GML, PALMETTO_CS_ID, 1200, 400, 19, 16);
		
		// Fig. 3
		/*Network1 task1 = new Network1("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network1 task2 = new Network1("task 2", INS_GML, INS_CS_ID, 1400, 200, 19, 16);
		Network1 task3 = new Network1("task 3", INS_GML, INS_CS_ID, 1600, 200, 19, 16);
		Network1 task4 = new Network1("task 4", INS_GML, INS_CS_ID, 1800, 200, 19, 16);
		Network1 task5 = new Network1("task 5", INS_GML, INS_CS_ID, 2000, 200, 19, 16);
		
		// Fig. 3
		Network1 task6 = new Network1("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network1 task7 = new Network1("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1400, 200, 19, 16);
		Network1 task8 = new Network1("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1600, 200, 19, 16);
		Network1 task9 = new Network1("task 9", PALMETTO_GML, PALMETTO_CS_ID, 1800, 200, 19, 16);
		Network1 task10 = new Network1("task 10", PALMETTO_GML, PALMETTO_CS_ID, 2000, 200, 19, 16);*/
		
		// Fig. 1
		/*Network1 task1 = new Network1("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network1 task2 = new Network1("task 2", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network1 task3 = new Network1("task 3", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network1 task4 = new Network1("task 4", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network1 task5 = new Network1("task 5", INS_GML, INS_CS_ID, 1200, 200, 19, 16);

		// Fig. 1
		Network1 task6 = new Network1("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network1 task7 = new Network1("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network1 task8 = new Network1("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network1 task9 = new Network1("task 9", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network1 task10 = new Network1("task 10", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);*/
		
		
		System.out.println("Starting Executor:");

		ExecutorService threadExecutor = Executors.newCachedThreadPool();
		threadExecutor.execute(task1);
		threadExecutor.execute(task2);
		threadExecutor.execute(task3);
		threadExecutor.execute(task4);
		threadExecutor.execute(task5);
		threadExecutor.execute(task6);
		threadExecutor.execute(task7);
		threadExecutor.execute(task8);
		threadExecutor.execute(task9);
		threadExecutor.execute(task10);
		
		threadExecutor.shutdown();
		System.out.println("Tasks started, main ends");
	}
}

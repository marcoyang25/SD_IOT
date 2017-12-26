package device;

import java.util.concurrent.*;

public class Executor {
	public static final String INS_GML = "C:\\Users\\marco\\git\\setcover\\setcover\\gml\\IowaStatewideFiberMap.gml";
	public static final String PALMETTO_GML = "C:\\Users\\marco\\git\\setcover\\setcover\\gml\\Palmetto.gml";
	//public static final String INS_GML = "/home/mnetlab/eclipse-workspace/SD_IOT/SD_IOT/gml/IowaStatewideFiberMap.gml";
	//public static final String PALMETTO_GML = "/home/mnetlab/eclipse-workspace/SD_IOT/SD_IOT/gml/Palmetto.gml";
	
	public static final int INS_CS_ID = 26; // Sioux Falls
	public static final int PALMETTO_CS_ID = 29; // Boone
	
	public static void main(String[] args) {
		
		// Fig. 1
		/*Network1 task1 = new Network1("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network1 task2 = new Network1("task 2", INS_GML, INS_CS_ID, 1200, 250, 19, 16);
		Network1 task3 = new Network1("task 3", INS_GML, INS_CS_ID, 1200, 300, 19, 16);
		Network1 task4 = new Network1("task 4", INS_GML, INS_CS_ID, 1200, 350, 19, 16);
		Network1 task5 = new Network1("task 5", INS_GML, INS_CS_ID, 1200, 400, 19, 16);
		
		// Fig. 1
		Network1 task6 = new Network1("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network1 task7 = new Network1("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1200, 250, 19, 16);
		Network1 task8 = new Network1("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1200, 300, 19, 16);
		Network1 task9 = new Network1("task 9", PALMETTO_GML, PALMETTO_CS_ID, 1200, 350, 19, 16);
		Network1 task10 = new Network1("task 10", PALMETTO_GML, PALMETTO_CS_ID, 1200, 400, 19, 16);*/
		
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
		
		// Fig. 4
		/*Network3 task1 = new Network3("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network3 task2 = new Network3("task 2", INS_GML, INS_CS_ID, 1400, 200, 19, 16);
		Network3 task3 = new Network3("task 3", INS_GML, INS_CS_ID, 1600, 200, 19, 16);
		Network3 task4 = new Network3("task 4", INS_GML, INS_CS_ID, 1800, 200, 19, 16);
		Network3 task5 = new Network3("task 5", INS_GML, INS_CS_ID, 2000, 200, 19, 16);

		// Fig. 4
		Network3 task6 = new Network3("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network3 task7 = new Network3("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1400, 200, 19, 16);
		Network3 task8 = new Network3("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1600, 200, 19, 16);
		Network3 task9 = new Network3("task 9", PALMETTO_GML, PALMETTO_CS_ID, 1800, 200, 19, 16);
		Network3 task10 = new Network3("task 10", PALMETTO_GML, PALMETTO_CS_ID, 2000, 200, 19, 16);*/
		
		// Fig. 5
		/*Network1 task1 = new Network1("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network1 task2 = new Network1("task 2", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network3 task3 = new Network3("task 3", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network3 task4 = new Network3("task 4", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);*/
		
		// Fig. 6
		/*Network3 task1 = new Network3("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network3 task2 = new Network3("task 2", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);*/
		
		// Fig. 7, 8, 9
		//Network1 task1 = new Network1("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		//Network1 task2 = new Network1("task 2", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);

		// Fig. 10
		/*Network1 task1 = new Network1("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 6);
		Network1 task2 = new Network1("task 2", INS_GML, INS_CS_ID, 1200, 200, 19, 8);
		Network1 task3 = new Network1("task 3", INS_GML, INS_CS_ID, 1200, 200, 19, 10);
		Network1 task4 = new Network1("task 4", INS_GML, INS_CS_ID, 1200, 200, 19, 12);
		Network1 task5 = new Network1("task 5", INS_GML, INS_CS_ID, 1200, 200, 19, 14);
		Network1 task6 = new Network1("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 6);
		Network1 task7 = new Network1("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 8);
		Network1 task8 = new Network1("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 10);
		Network1 task9 = new Network1("task 9", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 12);
		Network1 task10 = new Network1("task 10", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 14);*/
		
		// Fig. 11, 12
		/*Network1 task1 = new Network1("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network1 task2 = new Network1("task 2", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);*/
		
		// Fig. 13
		// Real task1 = new Real("task 1", INS_GML, INS_CS_ID, 0, 0, 2, 2);
		
		// Fig. 14
		/*Network2 task1 = new Network2("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 4);
		Network2 task2 = new Network2("task 2", INS_GML, INS_CS_ID, 1200, 200, 19, 8);
		Network2 task3 = new Network2("task 3", INS_GML, INS_CS_ID, 1200, 200, 19, 12);
		Network2 task4 = new Network2("task 4", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		
		Network2 task5 = new Network2("task 5", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 4);
		Network2 task6 = new Network2("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 8);
		Network2 task7 = new Network2("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 12);
		Network2 task8 = new Network2("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);*/
		
		// Fig. 15
		/*Network2 task1 = new Network2("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 4);
		Network2 task2 = new Network2("task 2", INS_GML, INS_CS_ID, 1200, 200, 19, 8);
		Network2 task3 = new Network2("task 3", INS_GML, INS_CS_ID, 1200, 200, 19, 12);
		Network2 task4 = new Network2("task 4", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		
		Network2 task5 = new Network2("task 5", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 4);
		Network2 task6 = new Network2("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 8);
		Network2 task7 = new Network2("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 12);
		Network2 task8 = new Network2("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);*/
		
		// Fig. 16
		/*Network2 task1 = new Network2("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 6);
		Network2 task2 = new Network2("task 2", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 6);
		Network2 task3 = new Network2("task 3", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network2 task4 = new Network2("task 4", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);*/
		
		// Fig. 22
		/*Network2 task1 = new Network2("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 6);
		Network2 task2 = new Network2("task 2", INS_GML, INS_CS_ID, 1200, 200, 19, 8);
		Network2 task3 = new Network2("task 3", INS_GML, INS_CS_ID, 1200, 200, 19, 10);
		Network2 task4 = new Network2("task 4", INS_GML, INS_CS_ID, 1200, 200, 19, 12);
		Network2 task5 = new Network2("task 5", INS_GML, INS_CS_ID, 1200, 200, 19, 14);
		Network2 task6 = new Network2("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 6);
		Network2 task7 = new Network2("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 8);
		Network2 task8 = new Network2("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 10);
		Network2 task9 = new Network2("task 9", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 12);
		Network2 task10 = new Network2("task 10", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 14);*/
		
		// Fig. 24
		/*Network1 task1 = new Network1("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16, 5);
		Network1 task2 = new Network1("task 2", INS_GML, INS_CS_ID, 1200, 200, 19, 16, 6);
		Network1 task3 = new Network1("task 3", INS_GML, INS_CS_ID, 1200, 200, 19, 16, 7);
		Network1 task4 = new Network1("task 4", INS_GML, INS_CS_ID, 1200, 200, 19, 16, 8);
		Network1 task5 = new Network1("task 5", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16, 5);
		Network1 task6 = new Network1("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16, 6);
		Network1 task7 = new Network1("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16, 7);
		Network1 task8 = new Network1("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16, 8);*/
		
		// Fig. 1
		/*Network1 task1 = new Network1("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network1 task2 = new Network1("task 2", INS_GML, INS_CS_ID, 1400, 200, 19, 16);
		Network1 task3 = new Network1("task 3", INS_GML, INS_CS_ID, 1600, 200, 19, 16);
		Network1 task4 = new Network1("task 4", INS_GML, INS_CS_ID, 1800, 200, 19, 16);
		Network1 task5 = new Network1("task 5", INS_GML, INS_CS_ID, 2000, 200, 19, 16);
		
		Network1 task6 = new Network1("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network1 task7 = new Network1("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1400, 200, 19, 16);
		Network1 task8 = new Network1("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1600, 200, 19, 16);
		Network1 task9 = new Network1("task 9", PALMETTO_GML, PALMETTO_CS_ID, 1800, 200, 19, 16);
		Network1 task10 = new Network1("task 10", PALMETTO_GML, PALMETTO_CS_ID, 2000, 200, 19, 16);*/
		
		// Fig. 2
		/*Network3 task1 = new Network3("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network3 task2 = new Network3("task 2", INS_GML, INS_CS_ID, 1400, 200, 19, 16);
		Network3 task3 = new Network3("task 3", INS_GML, INS_CS_ID, 1600, 200, 19, 16);
		Network3 task4 = new Network3("task 4", INS_GML, INS_CS_ID, 1800, 200, 19, 16);
		Network3 task5 = new Network3("task 5", INS_GML, INS_CS_ID, 2000, 200, 19, 16);

		Network3 task6 = new Network3("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network3 task7 = new Network3("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1400, 200, 19, 16);
		Network3 task8 = new Network3("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1600, 200, 19, 16);
		Network3 task9 = new Network3("task 9", PALMETTO_GML, PALMETTO_CS_ID, 1800, 200, 19, 16);
		Network3 task10 = new Network3("task 10", PALMETTO_GML, PALMETTO_CS_ID, 2000, 200, 19, 16);*/
		
		/***************************************************12/07***************************************************/
		
		// Fig. 3
		Network3 task1 = new Network3("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		//Network3 task2 = new Network3("task 2", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		
		// Fig. 4
		/*Network3 task1 = new Network3("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network3 task2 = new Network3("task 2", INS_GML, INS_CS_ID, 1400, 200, 19, 16);
		Network3 task3 = new Network3("task 3", INS_GML, INS_CS_ID, 1600, 200, 19, 16);
		Network3 task4 = new Network3("task 4", INS_GML, INS_CS_ID, 1800, 200, 19, 16);
		Network3 task5 = new Network3("task 5", INS_GML, INS_CS_ID, 2000, 200, 19, 16);*/

		// Fig. 4
		/*Network3 task6 = new Network3("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network3 task7 = new Network3("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1400, 200, 19, 16);
		Network3 task8 = new Network3("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1600, 200, 19, 16);
		Network3 task9 = new Network3("task 9", PALMETTO_GML, PALMETTO_CS_ID, 1800, 200, 19, 16);
		Network3 task10 = new Network3("task 10", PALMETTO_GML, PALMETTO_CS_ID, 2000, 200, 19, 16);*/
		
		// Fig. 6
		/*Network2 task1 = new Network2("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 2);
		Network2 task2 = new Network2("task 2", INS_GML, INS_CS_ID, 1200, 200, 19, 4);
		Network2 task3 = new Network2("task 3", INS_GML, INS_CS_ID, 1200, 200, 19, 6);
		Network2 task4 = new Network2("task 4", INS_GML, INS_CS_ID, 1200, 200, 19, 8);
		Network2 task5 = new Network2("task 5", INS_GML, INS_CS_ID, 1200, 200, 19, 10);
		Network2 task6 = new Network2("task 6", INS_GML, INS_CS_ID, 1200, 200, 19, 12);
		Network2 task7 = new Network2("task 7", INS_GML, INS_CS_ID, 1200, 200, 19, 14);*/
		
		// Fig. 6
		/*Network2 task1 = new Network2("task 1", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 2);
		Network2 task2 = new Network2("task 2", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 4);
		Network2 task3 = new Network2("task 3", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 6);
		Network2 task4 = new Network2("task 4", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 8);
		Network2 task5 = new Network2("task 5", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 10);
		Network2 task6 = new Network2("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 12);
		Network2 task7 = new Network2("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 14);*/
		
		// running time
		// Network4
		//Network4 task1 = new Network4("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		/*Network4 task2 = new Network4("task 2", INS_GML, INS_CS_ID, 1400, 200, 19, 16);
		Network4 task3 = new Network4("task 3", INS_GML, INS_CS_ID, 1600, 200, 19, 16);
		Network4 task4 = new Network4("task 4", INS_GML, INS_CS_ID, 1800, 200, 19, 16);
		Network4 task5 = new Network4("task 5", INS_GML, INS_CS_ID, 2000, 200, 19, 16);*/
		
		// request
		/*Network3 task1 = new Network3("task 1", INS_GML, INS_CS_ID, 1200, 150, 19, 16);
		Network3 task2 = new Network3("task 2", INS_GML, INS_CS_ID, 1200, 180, 19, 16);
		Network3 task3 = new Network3("task 3", INS_GML, INS_CS_ID, 1200, 210, 19, 16);
		Network3 task4 = new Network3("task 4", INS_GML, INS_CS_ID, 1200, 240, 19, 16);*/

		// request
		/*Network3 task6 = new Network3("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 150, 19, 16);
		Network3 task7 = new Network3("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1200, 180, 19, 16);
		Network3 task8 = new Network3("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1200, 210, 19, 16);
		Network3 task9 = new Network3("task 9", PALMETTO_GML, PALMETTO_CS_ID, 1200, 240, 19, 16);*/
		
		// gamma
		/*Network1 task1 = new Network1("task 1", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network1 task2 = new Network1("task 2", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);*/
		
		// latency
		/*Network5 task1 = new Network5("task 1", INS_GML, INS_CS_ID, 1200, 100, 19, 16);
		Network5 task2 = new Network5("task 2", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network5 task3 = new Network5("task 3", INS_GML, INS_CS_ID, 1200, 300, 19, 16);
		Network5 task4 = new Network5("task 4", INS_GML, INS_CS_ID, 1200, 400, 19, 16);
		
		Network5 task5 = new Network5("task 5", PALMETTO_GML, PALMETTO_CS_ID, 1200, 100, 19, 16);
		Network5 task6 = new Network5("task 6", PALMETTO_GML, PALMETTO_CS_ID, 1200, 200, 19, 16);
		Network5 task7 = new Network5("task 7", PALMETTO_GML, PALMETTO_CS_ID, 1200, 300, 19, 16);
		Network5 task8 = new Network5("task 8", PALMETTO_GML, PALMETTO_CS_ID, 1200, 400, 19, 16);*/
		
		// running time and latency
		/*Network4 task1 = new Network4("task 1", INS_GML, INS_CS_ID, 1200, 100, 19, 16);
		Network4 task2 = new Network4("task 2", INS_GML, INS_CS_ID, 1200, 200, 19, 16);
		Network4 task3 = new Network4("task 3", INS_GML, INS_CS_ID, 1200, 300, 19, 16);
		Network4 task4 = new Network4("task 4", INS_GML, INS_CS_ID, 1200, 400, 19, 16);*/
		
		
		System.out.println("Starting Executor:");

		ExecutorService threadExecutor = Executors.newCachedThreadPool();
		threadExecutor.execute(task1);
		/*threadExecutor.execute(task2);
		threadExecutor.execute(task3);
		threadExecutor.execute(task4);
		threadExecutor.execute(task5);
		threadExecutor.execute(task6);
		threadExecutor.execute(task7);
		threadExecutor.execute(task8);
		threadExecutor.execute(task9);
		threadExecutor.execute(task10);*/
		
		threadExecutor.shutdown();
		System.out.println("Tasks started, main ends");
	}
}

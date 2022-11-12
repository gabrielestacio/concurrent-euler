/*THIS JAVA CLASS IS PART OF THE CONCURRENTEULER PROJECT, A WORK OF THE 2022.2 CLASS 'CONCURRENT PROGRAMMING'.
 * THE AUTHOR OF THIS PROJECT IS THE STUDENT GABRIEL ESTÁCIO DE SOUZA PASSOS*/
package concurrent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.*;

public class FixedPool{
	public static void main(String[] args) throws InterruptedException {
		/*NECESSARILY, TWO ARGUMENTS NEED TO BE PASSED: THE NUMBER OF TASKS AND THE NUMBER OF THREADS, IN THIS ORDER*/
		if(args.length != 2) {
			System.err.println("Número errado de argumentos.");
			System.exit(1);
		}
		
		/*THAT LIST WORK WITH A KIND OF LOCK SYSTEM, WHERE A THREAD CANNOT MODIFY THE LIST WHEN OTHER THREAD IS ALREADY DOING IT
		 * THE LIST IS USED TO STORE EVERY PIECE OF THE FINAL RESULT NUMBER THAT IS CALCULATED BY EACH THREAD
		 * *THE AGGREGATOR TYPE HAS THE RESPONSIBILITY OF JOIN ALL THE PARTS OF THE NUMBER, PRODUCING THE FINAL RESULT
		 * THE RESPONSIBILITY OF THE CYCLICBARRIER IS TO ENSURE THAT EVERY RUNNING THREAD WILL BE DONE BEFORE STARTS THE NEXT ITERATION
		 * THE EXECUTOR SERVICE WILL CONTROL AN EXECUTOR OBJECT, THAT WILL MANAGE A THREADPOOL, RESPONSIBLE FOR CONTROL A FIXED NUMBER OF CREATED THREADS.
		 * 		THE EXECUTOR OBJECT WILL DO SOME OPERATIONS, SUCH AS INVOKING, SCHEDULLING AND CONTROLLING TASKS.
		 * 		SO, THE EXECUTOR SERVICE TO CONTROL THE EXECUTOR OBJECT BEHAVIOR, TERMINATING IT, VERIFYING IF A TASK IS ALREADY FINISHED OR CANCELING IT*/
		List<BigDecimal> terms = Collections.synchronizedList(new ArrayList<BigDecimal>());
		Aggregator aggregator = new Aggregator(terms, Integer.parseInt(args[0]));
		CyclicBarrier barrier = new CyclicBarrier(Integer.parseInt(args[1]), aggregator);
		ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(args[1]));

		/*THIS LOOP WILL BUILD AND EXECUTE ALL THE TASKS OF THE TYPE EULERTASK, THAT WILL CALCULATE A TERM OF INFINITE SERIES.*/
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			Runnable task = new EulerTask(i, terms, barrier);
			executor.execute(task);
		}
		
		/*HERE, WE PRINT THE NUMBER OF ACTIVE THREADS, AS REQUESTED IN PROJECT'S SPECIFICATIONS*/
		System.out.println("THE NUMBER OF ACTIVE THREADS IS " + Thread.activeCount());
		
		/*THE EXECUTOR OBJECT IS TERMINATED IMMEDIATELY*/
		executor.shutdownNow();
	}
}
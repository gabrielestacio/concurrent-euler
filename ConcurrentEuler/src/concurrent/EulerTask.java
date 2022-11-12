/*THIS JAVA CLASS IS PART OF THE CONCURRENTEULER PROJECT, A WORK OF THE 2022.2 CLASS 'CONCURRENT PROGRAMMING'.
 * THE AUTHOR OF THIS PROJECT IS THE STUDENT GABRIEL ESTÁCIO DE SOUZA PASSOS*/
package concurrent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class EulerTask implements Runnable {
	private int i; //NUMBER OF THE I-TH TERM OF THE SEQUENCE
	private List<BigDecimal> terms; //LIST OF THE SEQUENCE'S TERMS
	private CyclicBarrier barrier; //BARRIER THAT CONTROL THE END OF THE ITERATION
	
	/*PARAMETERIZED CONSTRUCTOR*/
	public EulerTask(int i, List<BigDecimal> terms, CyclicBarrier barrier) {
		this.i = i;
		this.terms = terms;
		this.barrier = barrier;
	}
	
	/*FACTORIAL*/
	public BigDecimal factorial(double number) {
		BigDecimal result = new BigDecimal(1);
		
		while(number != 0) {
			result = result.multiply(new BigDecimal(number));
			number--;
		}
		
		return result;
	}
	
	@Override
	public void run() {
		BigDecimal term = BigDecimal.ONE.divide(factorial(i), 50, RoundingMode.HALF_UP); /*THE DIVISION THAT FIND THE I-TH TERM OF THE SEQUENCE*/
		terms.add(term); /*ADD THE FOUND TERM TO THE LIST OF THE TERMS*/
		
		try {
			barrier.await(); /*THREAD WAITS TO THE OTHER RUNNING THREADS TO BE DONE*/
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}
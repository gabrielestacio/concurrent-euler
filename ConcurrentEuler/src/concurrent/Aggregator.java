/*THIS JAVA CLASS IS PART OF THE CONCURRENTEULER PROJECT, A WORK OF THE 2022.2 CLASS 'CONCURRENT PROGRAMMING'.
 * THE AUTHOR OF THIS PROJECT IS THE STUDENT GABRIEL ESTÁCIO DE SOUZA PASSOS*/
package concurrent;

import java.util.List;
import java.math.BigDecimal;

public class Aggregator implements Runnable{
	private List<BigDecimal> terms; //LIST OF THE SEQUENCE'S TERMS
	private int size = 0; //THE LIST'S FINAL SIZE
	
	/*PARAMETERIZED CONSTRUCTOR*/
	public Aggregator(List<BigDecimal> terms, int size) {
		this.terms = terms;
		this.size = size;
	}
	
	@Override
	public void run() {
		BigDecimal result = BigDecimal.ZERO;
		
		/*THIS LOOP AGGREGATE ALL THE TERMS ALREADY ADDED TO THE TERM'S LIST*/
		for(int i = 0; i < terms.size(); i++) {
			result = result.add(terms.get(i));
		}
		
		/*THE RESULT WILL ONLY BE PRINTED WHEN THE LIST REACH ITS FINAL SIZE, I.E., WHEN THE FINAL RESULT IS FOUND*/
		if(terms.size() == size) {
			System.out.println("THE NUMBER e WITH " + size + " DECIMAL PLACES IS " + result);
		}
	}
}
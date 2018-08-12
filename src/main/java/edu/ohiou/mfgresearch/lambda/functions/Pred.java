package edu.ohiou.mfgresearch.lambda.functions;

/**
 * Functional Interface implementing Boolean Type, which is used 
 * to perform a yes/no test.
 * Similar to Predicate in JDK but throws Exception 
 * @see https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html
 * @author arko
 * @param <T>
 */
public interface Pred<T> {
	
	boolean test(T t) throws Exception;

}

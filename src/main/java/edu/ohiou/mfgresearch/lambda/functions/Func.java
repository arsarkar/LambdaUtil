package edu.ohiou.mfgresearch.lambda.functions;

/**
 * Functional Interface implementing Function
 * Similar to Function in JDK but throws Exception 
 * @see https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html
 * @author arko
 * @param <T>
 */
public interface Func<T, U> {
	
	U apply(T t) throws Exception;

}

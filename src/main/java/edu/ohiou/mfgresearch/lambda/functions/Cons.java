package edu.ohiou.mfgresearch.lambda.functions;

/**
 * Functional Interface implementing Void Type, which is the type for the result 
 * of a function that returns normally, but does not provide a result value.
 * Similar to Consumer in JDK but throws Exception 
 * @see https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html
 * @author arko
 * @param <T>
 */
public interface Cons<T> {

	void accept(T t) throws Exception;
	
}

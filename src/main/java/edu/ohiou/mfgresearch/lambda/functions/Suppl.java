package edu.ohiou.mfgresearch.lambda.functions;

/**
 * Functional Interface implementing Supplier type
 * Similar to Supplier in JDK but throws Exception 
 * @see https://docs.oracle.com/javase/8/docs/api/java/util/function/Supplier.html
 * @author arko
 * @param <T>
 */
public interface Suppl<T> {
	
	T get() throws Exception;

}

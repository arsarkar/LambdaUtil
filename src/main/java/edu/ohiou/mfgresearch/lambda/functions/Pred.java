package edu.ohiou.mfgresearch.lambda.functions;

public interface Pred<T> {
	
	boolean test(T t) throws Exception;

}

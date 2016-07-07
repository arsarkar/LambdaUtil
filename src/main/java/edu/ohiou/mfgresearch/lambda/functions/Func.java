package edu.ohiou.mfgresearch.lambda.functions;

public interface Func<T, U> {
	
	U apply(T t) throws Exception;

}

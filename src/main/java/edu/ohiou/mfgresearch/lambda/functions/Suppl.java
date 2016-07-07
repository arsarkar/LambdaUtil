package edu.ohiou.mfgresearch.lambda.functions;

public interface Suppl<T> {
	
	T get() throws Exception;

}

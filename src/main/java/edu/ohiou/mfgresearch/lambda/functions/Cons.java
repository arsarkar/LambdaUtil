package edu.ohiou.mfgresearch.lambda.functions;

public interface Cons<T> {

	void accept(T t) throws Exception;
	
}

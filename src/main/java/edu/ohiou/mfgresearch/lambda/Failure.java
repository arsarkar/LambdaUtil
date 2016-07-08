package edu.ohiou.mfgresearch.lambda;

import java.util.function.Consumer;
import java.util.stream.Stream;

import edu.ohiou.mfgresearch.lambda.functions.Cons;
import edu.ohiou.mfgresearch.lambda.functions.Func;
import edu.ohiou.mfgresearch.lambda.functions.Pred;

public class Failure<T> extends Anything<T> {
	
	private Exception exception;
	
	public Failure(Exception e) {
		this.exception = e;
	}

	@Override
	public Failure<T> onFailure(Consumer<Exception> c) {
		c.accept(exception);
		return new Failure<T>(exception);
	}

	@Override
	public <U> Failure<U> map(Func<T, U> f) {
		return new Failure<U>(exception);
	}

	@Override
	public <U> Anything<U> fMap(Func<T, Anything<U>> f) {
		return new Failure<U>(exception);
	}

	@Override
	public <U> Stream<Anything<U>> fMap2Stream(Func<T, Stream<Anything<U>>> f) {
		return Stream.of(new Failure<U>(exception));
	}

	@Override
	public Anything<T> filter(Pred<T> p) {
		return new Failure<T>(exception);
	}
	

	@Override
	public Failure<T> set(Cons<T> c) {
		return new Failure<T>(exception);
	}

	//get cannot be a legit way, this will again creep in null pointer here
	//use onSuccess to deal with end operations.
	@Override
	public T get() {
		return null;
	}

	@Override
	public void onSuccess(Consumer<T> c) {
		//do nothing
	}

	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return false;
	}
	

}

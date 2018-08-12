package edu.ohiou.mfgresearch.lambda;

import java.util.function.Consumer;
import java.util.stream.Stream;

import edu.ohiou.mfgresearch.lambda.functions.Cons;
import edu.ohiou.mfgresearch.lambda.functions.Func;
import edu.ohiou.mfgresearch.lambda.functions.Pred;

public class Failure<T> extends Algo<T> {
	
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
	public <U> Omni<U> fMap(Func<T, Omni<U>> f) {
		return Omni.nihil();
	}

	@Override
	public <U> Stream<Algo<U>> fMap2Stream(Func<T, Stream<Algo<U>>> f) {
		return Stream.of(new Failure<U>(exception));
	}

	@Override
	public Algo<T> filter(Pred<T> p) {
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

	@Override
	public Algo<T> select(Pred<T> p, Cons<T> c) {
		return new Failure<T>(exception);
	}
	

}

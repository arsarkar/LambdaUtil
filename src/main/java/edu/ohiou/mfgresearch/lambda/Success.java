package edu.ohiou.mfgresearch.lambda;

import java.util.function.Consumer;
import java.util.stream.Stream;

import edu.ohiou.mfgresearch.lambda.functions.Cons;
import edu.ohiou.mfgresearch.lambda.functions.Func;
import edu.ohiou.mfgresearch.lambda.functions.Pred;

public class Success<T> extends Anything<T> {
	
	public Success(T obj){
		super(obj);
	}

	@Override
	public Success<T> onFailure(Consumer<Exception> c) {
		return this;
	}

	@Override
	public <U> Anything<U> map(Func<T, U> f) {
		try {
			return new Success<U>(f.apply(object));
		} catch (Exception e) {
			return new Failure<U>(e);
		}
	}

	@Override
	public <U> Anything<U> fMap(Func<T, Anything<U>> f) {
		
		try {
			return f.apply(object);
		} catch (Exception e) {
			return new Failure<U>(e);
		}
	}


	@Override
	public <U> Stream<Anything<U>> fMap2Stream(Func<T, Stream<Anything<U>>> f) {
		
		try {
			return f.apply(object);
		} catch (Exception e) {
			return Stream.of(new Failure<U>(e));
		}
	}

	@Override
	public Anything<T> filter(Pred<T> p) {
		try {
			if (p.test(object)) 
				return new Success<T>(object);
			else
				return new Failure<T>(new Exception());
		} catch (Exception e) {
			return new Failure<T>(e);
		}
	}

	@Override
	public Anything<T> set(Cons<T> c) {
		try {
			c.accept(object);
		} catch (Exception e) {
			return new Failure<T>(e);
		}
		return this;
	}	


	@Override
	public void onSuccess(Consumer<T> c) {
		c.accept(object);
	}
	
	/**
	 * utility method only for successful objects 
	 * not recommended for pure lambda
	 * @return
	 */
	@Override
	public T get() {
		return object;
	}

	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return true;
	}

}

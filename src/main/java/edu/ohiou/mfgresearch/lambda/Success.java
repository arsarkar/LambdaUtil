package edu.ohiou.mfgresearch.lambda;

import java.util.function.Consumer;
import java.util.stream.Stream;

import edu.ohiou.mfgresearch.lambda.functions.Cons;
import edu.ohiou.mfgresearch.lambda.functions.Func;
import edu.ohiou.mfgresearch.lambda.functions.Pred;

public class Success<T> extends Uni<T> {
	
	public Success(T obj){
		super(obj);
	}

	@Override
	public Success<T> onFailure(Cons<Exception> c) {
		return this;
	}

	@Override
	public <U> Uni<U> map(Func<T, U> f) {
		try {
			return new Success<U>(f.apply(object));
		} catch (Exception e) {
			return new Failure<U>(e);
		}
	}

	/**
	 * Monadic flat map
	 * converts the algo to Omni
	 * returns nihil when when exception occurs
	 */
	@Override
	public <U> Omni<U> fMap(Func<T, Omni<U>> f) {
		
		try {
			return f.apply(object);
		} catch (Exception e) {
			return Omni.nihil();
		}
	}


	@Override
	public <U> Stream<Uni<U>> fMap2Stream(Func<T, Stream<Uni<U>>> f) {
		
		try {
			return f.apply(object);
		} catch (Exception e) {
			return Stream.of(new Failure<U>(e));
		}
	}

	/**
	 * applies the given predicate on the object
	 * returns Failure if false, Success if true 
	 */
	@Override
	public Uni<T> filter(Pred<T> p) {
		try {
			if (p.test(object)) 
				return new Success<T>(object);
			else
				return new Failure<T>(new Exception());
		} catch (Exception e) {
			return new Failure<T>(e);
		}
	}

	/**
	 * Supplied consumer c consumes every "Success" element
	 * Failure elements are returned as it is 
	 * New Failure takes place of existing Success if exception occurs
	 * @param c
	 * @return
	 */
	@Override
	public Uni<T> set(Cons<T> c) {
		try {
			c.accept(object);
		} catch (Exception e) {
			return new Failure<T>(e);
		}
		return this;
	}	


	@Override
	public void onSuccess(Cons<T> c) {
		try {
			c.accept(object);
		} catch (Exception e) {
			//this is a dead end, please do not use a consumer which is too heavy
		}
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

	/**
	 * An utility method for implementing 'liberal' switch-case 
	 * (liberal - do not break)
	 * The consumer only consumes the object when the 
	 * predicate is true
	 */
	@Override
	public Uni<T> select(Pred<T> p, Cons<T> c) {
		try {
			if (p.test(object)) 
				return set(c);
			else
				return this;
		} catch (Exception e) {
			return this;
		}
	}

}

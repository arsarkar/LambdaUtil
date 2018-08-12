package edu.ohiou.mfgresearch.lambda;

import java.util.function.Consumer;
import java.util.stream.Stream;

import edu.ohiou.mfgresearch.lambda.functions.Cons;
import edu.ohiou.mfgresearch.lambda.functions.Func;
import edu.ohiou.mfgresearch.lambda.functions.Pred;
import edu.ohiou.mfgresearch.lambda.functions.Suppl;

public abstract class Algo<T> {
	
	protected T object;
	
	protected Algo(){
		
	}
	
	protected Algo(T obj){
		this.object = obj;
	}
	
	public static <T> Algo<T> of(Suppl<T> s){
		try {
			return new Success<T>(s.get());
		} catch (Exception e) {
			return new Failure<T>(e);
		}
	}
	
	public static <T> Algo<T> of(T obj){
		return obj!=null?new Success<T>(obj):new Failure<T>(new NullPointerException("Object is null"));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Stream<Algo<T>> of(T... objs){
		return Stream.of(objs).map(o->Algo.of(o));
	}
	
	public abstract <U> Algo<U> map(Func<T, U> f);
	
	public abstract <U> Omni<U> fMap(Func<T, Omni<U>> f);
	
	public abstract <U> Stream<Algo<U>> fMap2Stream(Func<T, Stream<Algo<U>>> f);
	
	public abstract Algo<T> filter(Pred<T> p);
	
	public abstract Algo<T> set(Cons<T> c);

	public abstract Algo<T> select(Pred<T> p, Cons<T> c);
	
	public abstract Algo<T> onFailure(Consumer<Exception> c);
	
	public abstract void onSuccess(Consumer<T> c);
	
	public abstract boolean isSuccess();
	
	public abstract T get();

}

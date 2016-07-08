package edu.ohiou.mfgresearch.lambda;

import java.util.function.Consumer;
import java.util.stream.Stream;

import edu.ohiou.mfgresearch.lambda.functions.Cons;
import edu.ohiou.mfgresearch.lambda.functions.Func;
import edu.ohiou.mfgresearch.lambda.functions.Pred;
import edu.ohiou.mfgresearch.lambda.functions.Suppl;

public abstract class Anything<T> {
	
	protected T object;
	
	protected Anything(){
		
	}
	
	protected Anything(T obj){
		this.object = obj;
	}
	
	public static <T> Anything<T> of(Suppl<T> s){
		try {
			return new Success<T>(s.get());
		} catch (Exception e) {
			return new Failure<T>(e);
		}
	}
	
	public static <T> Anything<T> of(T obj){
		return obj!=null?new Success<T>(obj):new Failure<T>(new NullPointerException("Object is null"));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Stream<Anything<T>> of(T... objs){
		return Stream.of(objs).flatMap(Anything::of);
	}
	
	public abstract <U> Anything<U> map(Func<T, U> f);
	
	public abstract <U> Anything<U> fMap(Func<T, Anything<U>> f);
	
	public abstract <U> Stream<Anything<U>> fMap2Stream(Func<T, Stream<Anything<U>>> f);
	
	public abstract Anything<T> filter(Pred<T> p);
	
	public abstract Anything<T> set(Cons<T> c);
	
	public abstract Anything<T> onFailure(Consumer<Exception> c);
	
	public abstract void onSuccess(Consumer<T> c);
	
	public abstract boolean isSuccess();
	
	public abstract T get();

}

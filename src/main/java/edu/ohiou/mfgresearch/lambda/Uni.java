package edu.ohiou.mfgresearch.lambda;

import java.util.function.Consumer;
import java.util.stream.Stream;

import edu.ohiou.mfgresearch.lambda.functions.Cons;
import edu.ohiou.mfgresearch.lambda.functions.Func;
import edu.ohiou.mfgresearch.lambda.functions.Pred;
import edu.ohiou.mfgresearch.lambda.functions.Suppl;

public abstract class Uni<T> {
	
	protected T object;
	
	protected Uni(){
		
	}
	
	protected Uni(T obj){
		this.object = obj;
	}
	
	public static <T> Uni<T> of(Suppl<T> s){
		try {
			return new Success<T>(s.get());
		} catch (Exception e) {
			return new Failure<T>(e);
		}
	}
	
	public static <T> Uni<T> of(T obj){
		return obj!=null?new Success<T>(obj):new Failure<T>(new NullPointerException("Object is null"));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Stream<Uni<T>> of(T... objs){
		return Stream.of(objs).map(o->Uni.of(o));
	}
	
	public abstract <U> Uni<U> map(Func<T, U> f);
	
	public abstract <U> Omni<U> fMap(Func<T, Omni<U>> f);
	
	public abstract <U> Stream<Uni<U>> fMap2Stream(Func<T, Stream<Uni<U>>> f);
	
	public abstract Uni<T> filter(Pred<T> p);
	
	public abstract Uni<T> set(Cons<T> c);

	public abstract Uni<T> select(Pred<T> p, Cons<T> c);
	
	public abstract Uni<T> selectMap(Pred<T> p, Func<T, T> f);
	
	public abstract Uni<T> onFailure(Cons<Exception> c);
	
	public abstract void onSuccess(Cons<T> c);
	
	public abstract boolean isSuccess();
	
	public abstract T get();

}

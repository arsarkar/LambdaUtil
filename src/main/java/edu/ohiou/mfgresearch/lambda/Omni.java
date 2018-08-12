package edu.ohiou.mfgresearch.lambda;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.ohiou.mfgresearch.lambda.functions.Cons;
import edu.ohiou.mfgresearch.lambda.functions.Func;
import edu.ohiou.mfgresearch.lambda.functions.Pred;

public class Omni<T> {

	List<Algo<T>> algos;

	protected Omni(){
		algos = new LinkedList<Algo<T>>();
	}
	
	/**
	 * constructor accepting a list of items
	 * already wrapped in Algo
	 * @param collection
	 */
	protected Omni(List<Algo<T>> collection){
		algos = collection;
	}

	/**
	 * Utility constructor accepting a list of items
	 * internally wraps each item with Algo
	 * @param collection
	 */
	public static <T> Omni<T> of(List<T> collection){
		List<Algo<T>> set =
		collection.stream()
					.map(Algo::of)
					.collect(Collectors.toList());
		return new Omni<T>(set);
	}
	
	/**
	 * Returns an empty Omni, which is represented
	 * by just one Failure
	 * @return
	 */
	public static <T> Omni<T> nihil(){
		List<Algo<T>> nil = new LinkedList<Algo<T>>();
		nil.add(new Failure<T>(new Exception("nihil")));
		return new Omni<T>(nil);
	}
	
	/**
	 * Supplied consumer c consumes every "Success" element
	 * Failure elements are returned as it is 
	 * New Failure takes place of existing Success if exception occurs
	 * @param c
	 * @return
	 */
	public Omni<T> set(Cons<T> c){
		List<Algo<T>> res =
		algos.stream()
			.map(o->o.set(c))
			.collect(Collectors.toList());
		return new Omni<T>(res);
	}

	/**
	 * Applies the given predicate on every element
	 * returns Omni with only Success elements
	 * @param p
	 * @return
	 */
	public Omni<T> filter(Pred<T> p) {
		List<Algo<T>> res =
				algos.stream()
					.map(o->o.filter(p))
					.filter(o->o instanceof Success)
					.collect(Collectors.toList());
		return new Omni<T>(res);
	}
	
	/**
	 * Monadic map method 
	 * @param f
	 * @return
	 */
	public <R> Omni<R> map(Func<T,R> f){
		List<Algo<R>> res =
		algos.stream()
			 .map(o->o.map(f))
				.collect(Collectors.toList());		
		return new Omni<R>(res);
	}

	/**
	 * Monadic flatmap method
	 * @param f
	 * @return
	 */
	public <R> Omni<R> fMap(Func<T, Omni<R>> f){
		List<Algo<R>> res =
				algos.stream()
					.flatMap(o->{
						Omni<R> res1 = null;
						try {
							res1 = f.apply(o.get());
						} catch (Exception e) {
							return Stream.of(new Failure<R>(e));
						}
						return res1.toStream();
					})
					.collect(Collectors.toList());	
		
		return new Omni<R>(res);
		
	}
	
	
	
	public Omni<T> add(Omni<T> omni){
		algos.addAll(omni.toList());
		return new Omni<T>(algos);
	}

	public Stream<Algo<T>> toStream(){
		return algos.stream();
	}

	public List<Algo<T>> toList(){
		return algos;
	}
}

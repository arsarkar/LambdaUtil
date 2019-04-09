package edu.ohiou.mfgresearch.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.ohiou.mfgresearch.lambda.functions.Cons;
import edu.ohiou.mfgresearch.lambda.functions.Func;
import edu.ohiou.mfgresearch.lambda.functions.Pred;

public class Omni<T> {

	List<Uni<T>> algos;

	protected Omni(){
		algos = new LinkedList<Uni<T>>();
	}

	/**
	 * constructor accepting a list of items
	 * already wrapped in Algo
	 * @param collection
	 */
	protected Omni(List<Uni<T>> collection){
		algos = collection;
	}

	/**
	 * Utility constructor accepting a list of items
	 * internally wraps each item with Algo
	 * @param collection
	 */
	public static <T> Omni<T> of(List<T> collection){
		List<Uni<T>> set =
				collection.stream()
				.map(Uni::of)
				.collect(Collectors.toList());
		return new Omni<T>(set);
	}

	/**
	 * Utility constructor accepting an array of items (declared as varag)
	 * internally wraps each item with Algo
	 * @param collection
	 */
	public static <T> Omni<T> of(T... collection){
		List<Uni<T>> set =
				Arrays.asList(collection).stream()
				.map(Uni::of)
				.collect(Collectors.toList());
		return new Omni<T>(set);
	}

	
	/**
	 * Returns an empty Omni, which is represented
	 * by just one Failure
	 * @return
	 */
	public static <T> Omni<T> nihil(){
		List<Uni<T>> nil = new LinkedList<Uni<T>>();
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
		List<Uni<T>> res =
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
		List<Uni<T>> res =
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
		List<Uni<R>> res =
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
		List<Uni<R>> res =
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

	/**
	 * Monadic select cons
	 * applies the given function on each element if the predicate is true
	 * @param p
	 * @param f
	 * @return
	 */
	public Omni<T> select(Pred<T> p, Cons<T> c){

		for(Uni<T> o: algos){
				if(o instanceof Success){
					if(o.filter(p) instanceof Success){
						o.set(c);	
					}
				}
			}
//mysterious problem while using the following code
//		algos.stream()
//			 .map(o->o.filter(p))
//			 .filter(o->o instanceof Success)
//			 .map(o->o.set(c));
		return this;
	}
	
	/**
	 * Monadic select cons
	 * applies the given function on each element if the predicate is true
	 * @param p
	 * @param f
	 * @return
	 */
	public Omni<T> selectAlt(Pred<T> p, Cons<T> c1, Cons<T> c2){

		for(Uni<T> o: algos){
				if(o instanceof Success){
					if(o.filter(p) instanceof Success){
						o.set(c1);	
					}
					else{
						o.set(c2);
					}
				}
			}
		return this;
	}
	
	/**
	 * Monadic select map
	 * applies the given function on each element if the predicate is true
	 * @param p
	 * @param f
	 * @return
	 */
	public Omni<T> selectMap(Pred<T> p, Func<T, T> f){
		List<Uni<T>> res = new LinkedList<Uni<T>>();
		for(Uni<T> o: algos){
			if(o instanceof Success){
				if(o.filter(p) instanceof Success){
					res.add(o.map(f));	
				}
				else{
					res.add(o);
				}
			}
		}		
		return new Omni<T>(res);
	}
	
	/**
	 * Find element by index
	 * @param index
	 * @return
	 */
	public Uni<T> find(int index){
		try{
			return Uni.of(algos.get(index).get());			
		}
		catch (IndexOutOfBoundsException e) {
			return new Failure<T>(e);
		}
	}
	
	/**
	 * Find the first element matching the given predicate 
	 * @param index
	 * @return
	 */
	public Uni<T> find(Pred<T> p){
		try{
			for(Uni<T> o: algos){
				if(o instanceof Success){
					if(o.filter(p) instanceof Success){
						return new Success<T>(o.get());	
					}
				}
			}		
		}
		catch (IndexOutOfBoundsException e) {
			return new Failure<T>(e);
		}
		return new Failure<T>(new NoSuchElementException("No element matched the given predicate!"));
	}

	/**
	 * immutable joining of two Omni
	 * @param omni
	 * @return
	 */
	public Omni<T> add(Omni<T> omni){
		algos.addAll(omni.algos);
		return new Omni<T>(algos);
	}

	public Stream<Uni<T>> toStream(){
		return algos.stream();
	}

	/**
	 * return the list of the objects of generic type
	 * @return
	 */
	public List<T> toList(){
		List<T> list = new LinkedList<T>();
		algos.forEach(u->list.add(u.get()));
		return list;
	}
	
	/**
	 * This is an end method, returns nothing
	 * Only failed uni are thrown to consumer.
	 * @param c
	 */
	public void onFailure(Cons<Exception> c){
				algos.stream()
				.filter(a->!a.isSuccess())
				.forEach(a->a.onFailure(c));
	}

	public boolean contains(T element) {
		return element==null?false:toList().contains(element);
	}
}

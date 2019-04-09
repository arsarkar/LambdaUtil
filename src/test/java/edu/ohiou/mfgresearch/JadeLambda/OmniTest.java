package edu.ohiou.mfgresearch.JadeLambda;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import edu.ohiou.mfgresearch.lambda.Uni;
import edu.ohiou.mfgresearch.lambda.Omni;
import edu.ohiou.mfgresearch.lambda.functions.Func;

public class OmniTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test1() {
		List<Integer> listOfStrings = new LinkedList<Integer>();
		listOfStrings.add(1);
		listOfStrings.add(2);
		listOfStrings.add(3);
		listOfStrings.add(4);
		listOfStrings.add(5);

		Omni.of(listOfStrings)
		.map(n->n*10)
		.set(n->System.out.println(n.toString()+","))
		;
	}

	@Test
	public void test2() {
		List<Integer> listOfStrings = new LinkedList<Integer>();
		listOfStrings.add(1);
		listOfStrings.add(-2);
		listOfStrings.add(3);
		listOfStrings.add(-4);
		listOfStrings.add(5);

		Omni.of(listOfStrings)
		.map(n->n*10)
		.filter(n->n>0)
		.set(n->System.out.println(n.toString()+","))
		;
	}

	@Test
	public void test3() {
		List<Integer> listOfStrings = new LinkedList<Integer>();
		listOfStrings.add(1);
		listOfStrings.add(-2);
		listOfStrings.add(3);
		listOfStrings.add(-4);
		listOfStrings.add(5);

		Omni.of(listOfStrings)
		.filter(n->n>0)
		.map(n->n.toString() + " birds! ")
		.set(s->System.out.println(s))
		;
	}

	@Test
	public void test4() {
		List<Integer> listOfStrings = new LinkedList<Integer>();
		listOfStrings.add(1);
		listOfStrings.add(-2);
		listOfStrings.add(3);
		listOfStrings.add(-4);
		listOfStrings.add(5);

		Func<Integer, Omni<String>> func = n->{
			List<String> res = new LinkedList<String>();
			IntStream.range(0, n)
			.forEach(i->res.add("s"+i));
			return Omni.of(res);
		};

		Omni.of(listOfStrings)
		.filter(n->n>0)
		.fMap(func)
		.set(s->System.out.println(s))
		;
	}

	@Test
	public void test5() {
		List<Integer> listOfStrings = new LinkedList<Integer>();
		listOfStrings.add(1);
		listOfStrings.add(-2);
		listOfStrings.add(3);
		listOfStrings.add(-4);
		listOfStrings.add(5);

		Omni.of(listOfStrings)
		.filter(n->n>0)
		.map(n->n*10)
		.map(n->"hey"+n)
		.set(s->System.out.println(s));

		Uni.of("Hello")
		.map(s->s.length())
		.set(l->System.out.println("lenght"+l));

		Uni.of(5)
		.map(n->n/0)
		.onFailure(e->System.out.println(e.getMessage()))
		.onSuccess(n->System.out.println(n))
		;

		Uni.of(3)
		.select(n->n%2==0, n->System.out.println("Even "+n))
		.select(n->n%2!=0, n->System.out.println("Odd " +n));

		//		Algo.of(4)
		//			.map(n->n/0)
		//			.onFailure(e->e.printStackTrace())
		//			.onSuccess(n->);
		//			;


		//		Algo.of("1,2,5,3,4")
		//			.fMap(s->Omni.of(new ArrayList(s.split(","))))
		//			.map(sn->Integer.parseInt(sn))
		//			.set(System.out::println)
		//			;
	}

	@Test
	public void omniSwitch(){
		List<Integer> listOfStrings = new LinkedList<Integer>();
		listOfStrings.add(1);
		listOfStrings.add(-2);
		listOfStrings.add(3);
		listOfStrings.add(-4);
		listOfStrings.add(5);
		listOfStrings.add(6);
		listOfStrings.add(-3);

		Omni.of(listOfStrings)
		.select(i->i>0, i->System.out.println(i+" is positive. "))
		.select(i->i<0, i->System.out.println(i+" is negative. "))
		.select(i->i%2==0, i->System.out.println(i+" is even. "))
		.select(i->i%2!=0, i->System.out.println(i+" is odd. "));
	}

	@Test
	public void omniFind(){
		List<Integer> listOfStrings = new LinkedList<Integer>();
		listOfStrings.add(1);
		listOfStrings.add(-2);
		listOfStrings.add(3);
		listOfStrings.add(-4);
		listOfStrings.add(5);
		listOfStrings.add(6);
		listOfStrings.add(-3);

		Omni.of(listOfStrings)
		.find(4)
		.onSuccess(i->System.out.println("Element at position 4 = "+ i));
		;

		Omni.of(listOfStrings)
		.find(i->i%2==0)
		.onSuccess(i->System.out.println("Element matching predicate = "+ i));
		;	
	}
	
	@Test
	public void omniSelect(){
		List<String> listOfStrings = new LinkedList<String>();
		listOfStrings.add("1");
		listOfStrings.add("-2");
		listOfStrings.add("3");
		listOfStrings.add("-4");
		listOfStrings.add("5");
		listOfStrings.add("6");
		listOfStrings.add("-3");

		Omni.of(listOfStrings)
			.selectMap(i->Integer.parseInt(i)<0, i->"Negative")
			.selectMap(i->Integer.parseInt(i)>=0, i->"Positive")
			.set(o->System.out.println(o))
		;	
	}
}

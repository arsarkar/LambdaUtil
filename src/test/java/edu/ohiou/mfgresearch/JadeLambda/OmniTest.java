package edu.ohiou.mfgresearch.JadeLambda;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import edu.ohiou.mfgresearch.lambda.Algo;
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
		
		Algo.of("Hello")
			.map(s->s.length())
			.set(l->System.out.println("lenght"+l));
		
		Algo.of(5)
			.map(n->n/0)
			.onFailure(e->e.printStackTrace())
			.onSuccess(n->System.out.println(n))
			;
			
		Algo.of(3)
			.select(n->n%2==0, n->System.out.println("Even"+n))
			.select(n->n%2!=0, n->System.out.println("Odd" +n));
	}
	
}

package edu.ohiou.mfgresearch.JadeLambda;

import java.util.stream.Stream;

import org.junit.Test;

import edu.ohiou.mfgresearch.lambda.Algo;
import edu.ohiou.mfgresearch.lambda.Failure;
import edu.ohiou.mfgresearch.lambda.Success;

/**
 * Unit test for simple App.
 */
public class AppTest
{
	@Test
	public void onSucessMap1(){
		Algo.of(String::new).map(t->{
			return 5.0;
		}).onSuccess(c->org.junit.Assert.assertEquals((Double)5.0, (Double)c));
	}
	
	@Test
	public void onSucessFMap1(){
		Algo.of(String::new).fMap2Stream(t->{
			return Stream.of(Algo.of(()->5.0), 
							 Algo.of(()->6.0), 
							 Algo.of(()->7.0));
		})
		.forEach(d->d.onSuccess(System.out::println));
	}
	
	@Test
	public void onSuccess3(){
		Algo.of(String::new).fMap2Stream(t->{
			return Stream.of(Algo.of(()->5.0), 
							 Algo.of(()->6.0), 
							 Algo.of(()->7.0));
		})
//		.map(d->d.fMap(v->{
//			return (v%2==0) ? new Success<Double>(v) :new Failure<Double>(new Exception("not even"));
//		}))
		.map(d->d.onFailure(e->System.out.println(e.getMessage())))
		.forEach(d->d.onSuccess(System.out::println));
		
	}
	
	@Test
	public void testComposition1(){
		
	}
   
}

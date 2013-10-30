package org.cs27x.adv.java.processors;

import java.util.Random;

import org.cs27x.adv.java.Invocation;
import org.cs27x.adv.java.InvocationProcessor;
import org.cs27x.adv.java.directives.RandNum;

public class RandNumProcessor implements InvocationProcessor<RandNum> {
	Random randomGenerator = new Random();

	@Override
	public void preProcess(Invocation i) {
		System.out.println("Generate a random number: "
				+ randomGenerator.nextInt(Integer.MAX_VALUE));
	}

	@Override
	public void postProcess(Invocation i) {
		System.out.println("Generate a random number: "
				+ randomGenerator.nextInt(Integer.MAX_VALUE));
	}

	@Override
	public void setAnnotation(RandNum anno) {
	}

}

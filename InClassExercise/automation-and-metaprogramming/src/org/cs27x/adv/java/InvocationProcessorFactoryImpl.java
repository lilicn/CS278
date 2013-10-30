package org.cs27x.adv.java;

import java.lang.annotation.Annotation;
import java.util.HashMap;

import org.cs27x.adv.java.directives.Echo;
import org.cs27x.adv.java.directives.RandNum;
import org.cs27x.adv.java.directives.TimeMe;
import org.cs27x.adv.java.processors.EchoProcessor;
import org.cs27x.adv.java.processors.RandNumProcessor;
import org.cs27x.adv.java.processors.TimeMeProcessor;
import org.cs27x.adv.java.test.TestDirective;
import org.cs27x.adv.java.test.TestProcessor;

public class InvocationProcessorFactoryImpl implements InvocationProcessorFactory {

	private HashMap<Class<?>, InvocationProcessor<?>> hm = new HashMap<Class<?>, InvocationProcessor<?>>();
	
	public InvocationProcessorFactoryImpl(){
		addProcessor(Echo.class, new EchoProcessor());
		addProcessor(RandNum.class,new RandNumProcessor());
		addProcessor(TimeMe.class,new TimeMeProcessor());
		addProcessor(TestDirective.class, new TestProcessor());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <PreProc extends Annotation> InvocationProcessor<PreProc> getProcessor(PreProc proc) {
		// TODO Auto-generated method stub
		return  (InvocationProcessor<PreProc>) hm.get(proc.annotationType());
	}
	
	@Override
	public void addProcessor(Class<?> c, InvocationProcessor<?> invoProc){
		hm.put(c,invoProc);
	}

}

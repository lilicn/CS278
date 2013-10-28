package edu.vanderbilt.cs278.lili.pubnubclient;

/**
 * MyExecutorFactory is used in the main activity to get the specific Executor
 * via message type. Now only ColorExecutor is registered.
 * 
 * @author Li
 * 
 */
public class MyExecutorFactory extends ExecutorFactory {

	public MyExecutorFactory() {
		setExec("color", new ColorExecutor());
	}
}

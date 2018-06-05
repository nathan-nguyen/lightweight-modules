import java.util.*;

public class ThreadRunnable implements Runnable {
	static class PrintObject {
		String output = "";

		public void print(boolean usingSop, String threadName){
			for (int i = 0; i < 10; ++i){
				if (usingSop) System.out.println(threadName + " " + i);
				else output += (threadName + " " + i + "\n");
			}
		}

		public synchronized void synchPrint(boolean usingSop, String threadName){
                        for (int i = 0; i < 10; ++i){
                                if (usingSop) System.out.println(threadName + " " + i); 
                                else output += (threadName + " " + i + "\n");
                        }   
                }
	}

	private PrintObject printObject;
	private String name;
	private boolean isSynch;
	private boolean usingSop;

	public void run(){
		if (isSynch) printObject.synchPrint(usingSop, name);
		else printObject.print(usingSop, name);
	}

	public ThreadRunnable(PrintObject printObject, String name, boolean isSynch, boolean usingSop){
		this.printObject = printObject;
		this.name = name;
		this.isSynch = isSynch;
		this.usingSop = usingSop;
	}

	public static void main(String[] args){
		PrintObject printObject = new PrintObject();

		boolean usingSop = "true".equals(args[0]);
		boolean isSynch = "true".equals(args[1]);
		boolean isJoin = "true".equals(args[2]);

		Thread[] t = new Thread[10];

		for (int i = 0; i < 10; ++i) t[i] = new Thread(new ThreadRunnable(printObject, "thread" + i, isSynch, usingSop));
		for (Thread thread: t) thread.start();

		if (isJoin){
			for (Thread thread : t){
				try {
					thread.join();
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
		}

		if (!usingSop) System.out.println(printObject.output);
	}
}

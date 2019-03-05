package dk.cphbusiness;

public class Helper {
    public static double timeExecutionInNanoTime(Runnable func) {
        long startTime = System.nanoTime();
        func.run();
        long endTime = System.nanoTime();

        return (double) (endTime - startTime) / 1000000;
    }
}

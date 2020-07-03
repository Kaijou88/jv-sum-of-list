package sum.lists;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumWithForkJoin extends RecursiveTask<Long> {
    private final List list;
    private final int firstElement;
    private final int lastElement;
    private final int threshold;

    public SumWithForkJoin(List list, int firstElement, int lastElement) {
        this.list = list;
        this.firstElement = firstElement;
        this.lastElement = lastElement;
        this.threshold = lastElement / Runtime.getRuntime().availableProcessors() + 1;
    }

    @Override
    protected Long compute() {
        int length = lastElement - firstElement;
        if (length <= threshold) {
            return sum();
        }
        SumWithForkJoin firstTask = new SumWithForkJoin(list,
                                                        firstElement,
                                              firstElement + length / 2);
        firstTask.fork();
        SumWithForkJoin secondTask = new SumWithForkJoin(list,
                                               firstElement + length / 2,
                                                         lastElement);
        Long secondTaskResult = secondTask.compute();
        Long firstTaskResult = firstTask.join();
        return firstTaskResult + secondTaskResult;
    }

    private long sum() {
        long result = 0;
        for (int i = firstElement; i < lastElement; i++) {
            result += (int) list.get(i);
        }
        return result;
    }

    public static long getSum(List list, int size) {
        SumWithForkJoin task = new SumWithForkJoin(list, 0, size);
        return new ForkJoinPool().invoke(task);
    }
}

package sum.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SumWithExecutorService {
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static long getSumWithExecutorService(List list, int amountOfThreads)
                                                            throws InterruptedException {
        int listSize = list.size();
        int numberOfSubLists = listSize / amountOfThreads;

        List<Callable<Long>> tasks = new ArrayList<>();
        for (int i = 0; i < listSize; i += numberOfSubLists) {
            List<Integer> subList = list.subList(i, Math.min(listSize, i + numberOfSubLists));
            tasks.add(() -> subList.stream().mapToLong(Integer::intValue).sum());
        }

        List<Future<Long>> result = executor.invokeAll(tasks);
        long res = result.stream()
                .map(x -> {
                    try {
                        return x.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .mapToLong(x -> x).sum();

        executor.shutdown();
        return res;
    }
}

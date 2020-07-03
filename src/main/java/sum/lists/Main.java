package sum.lists;

import java.util.List;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private static final int TOTAL_NUMBER = 1_000_000;
    private static final int THREADS = 10;

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = ListGeneration.generate(TOTAL_NUMBER);

        Long sumExecutorService = SumWithExecutorService.getSumWithExecutorService(list, THREADS);
        LOGGER.info("Sum using ExecutorService = " + sumExecutorService);

        Long sumForkJoin = SumWithForkJoin.getSum(list, TOTAL_NUMBER);
        LOGGER.info("Sum using ForkJoin = " + sumForkJoin);
    }
}

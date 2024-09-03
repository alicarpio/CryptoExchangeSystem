package ec.alina.task;

import ec.alina.domain.enums.CryptoType;
import ec.alina.domain.models.Exchange;

import java.math.BigDecimal;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class CryptoFluctuationTask implements  Task {
    private final Exchange exchange;
    private final Executor executor = Executors.newSingleThreadExecutor((runnable) -> {
        var thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.setName("KKCK");
        return thread;
    });
    private final AtomicInteger updateInterval;

    public CryptoFluctuationTask(Exchange exchange, int updateInterval) {
        this.exchange = exchange;
        this.updateInterval = new AtomicInteger(updateInterval);
    }

    public CryptoFluctuationTask(Exchange exchange) {
        this.exchange = exchange;
        this.updateInterval = new AtomicInteger(5000);
    }

    @Override
    public void runForeverAsync() {
        final var interrupted = new AtomicBoolean(false);
        final Supplier<Integer> getRandomInt = () -> ThreadLocalRandom.current().nextInt(500, 1000000);

        executor.execute(() -> {
            while (!interrupted.get()) {
                var interval = updateInterval.get();
                try {
                    Thread.sleep(interval);
                    exchange.setPriceFor(CryptoType.BTC, new BigDecimal(getRandomInt.get().toString()));
                    exchange.setPriceFor(CryptoType.ETH, new BigDecimal(getRandomInt.get().toString()));
                    exchange.setPriceFor(CryptoType.LTC, new BigDecimal(getRandomInt.get().toString()));
                } catch (InterruptedException ex) {
                    interrupted.set(true);
                }
            }
        });
    }
}

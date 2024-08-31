package ec.alina.domain.models;

import ec.alina.domain.enums.CrytoType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Exchange {
    private Map<CrytoType,Integer> initialFunds;
    private BlockingDeque<OrderTask> orders;

    public Exchange(Map<CrytoType,Integer> initialFunds) {
        this.initialFunds = new HashMap<>(initialFunds);
        orders = new LinkedBlockingDeque<>();
    }

    public Map<CrytoType, Integer> getInitialFunds() {
        return initialFunds;
    }

    public BlockingDeque<OrderTask> getOrders() {
        return orders;
    }
}

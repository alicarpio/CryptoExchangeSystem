package ec.alina.domain.models;

import ec.alina.domain.repositories.OrderFulfilledCallback;

public class OrderTask {
    private Object order;
    private OrderFulfilledCallback callback;

    public OrderTask(Object order, OrderFulfilledCallback callback) {
        this.order = order;
        this.callback = callback;
    }

    public Object getOrder() {
        return order;
    }

    public OrderFulfilledCallback getCallback() {
        return callback;
    }
}

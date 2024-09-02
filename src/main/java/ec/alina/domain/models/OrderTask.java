package ec.alina.domain.models;

public class OrderTask {
    private final Object order;
    private final OrderFulfilledCallback callback;

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

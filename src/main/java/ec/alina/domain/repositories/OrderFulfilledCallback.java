package ec.alina.domain.repositories;

@FunctionalInterface
public interface OrderFulfilledCallback<TOrder> {
    void onOrderFulfilled(TOrder order);
}

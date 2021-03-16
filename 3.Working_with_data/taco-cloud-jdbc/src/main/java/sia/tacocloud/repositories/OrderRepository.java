package sia.tacocloud.repositories;

import sia.tacocloud.domain.Order;

public interface OrderRepository {

    public Order save(Order order);
}

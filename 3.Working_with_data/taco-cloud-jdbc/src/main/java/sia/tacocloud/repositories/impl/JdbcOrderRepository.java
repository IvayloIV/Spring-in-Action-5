package sia.tacocloud.repositories.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import sia.tacocloud.domain.Order;
import sia.tacocloud.domain.Taco;
import sia.tacocloud.repositories.OrderRepository;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final SimpleJdbcInsert simpleJdbcOrderInsert;
    private final SimpleJdbcInsert simpleJdbcOrderTacoInsert;
    private final ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcOrderInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("Taco_Order")
            .usingGeneratedKeyColumns("id");

        this.simpleJdbcOrderTacoInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("Taco_Order_Tacos");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        long orderId = this.saveOrder(order);

        for (Taco taco: order.getTacos()) {
            this.saveTacoToOrder(taco, orderId);
        }

        return order;
    }

    private long saveOrder(Order order) {
        Map<String, Object> values = this.objectMapper.convertValue(order, Map.class);
        values.put("placedAt", new Date());
        return this.simpleJdbcOrderInsert.executeAndReturnKey(values).longValue();
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        this.simpleJdbcOrderTacoInsert.execute(values);
    }
}

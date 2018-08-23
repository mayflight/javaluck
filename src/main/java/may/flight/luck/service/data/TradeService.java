package may.flight.luck.service.data;

import may.flight.luck.entity.Trade;

public interface TradeService {
    void insertOrder(Trade order);
    Trade selectOrderByOrder(String orderId);
    void delete(Integer id);
}

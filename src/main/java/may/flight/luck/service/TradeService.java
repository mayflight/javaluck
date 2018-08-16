package may.flight.luck.service;

import may.flight.luck.entity.Trade;

public interface TradeService {
    void insertOrder(Trade order);
    Trade selectOrderByOrder(String orderId);
    void delete(int id);
}

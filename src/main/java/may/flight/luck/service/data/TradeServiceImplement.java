package may.flight.luck.service.data;

import may.flight.luck.dao.TradeDAO;
import may.flight.luck.entity.Trade;
import may.flight.luck.entity.TradeExample;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("orderService")
public class TradeServiceImplement implements TradeService {

    @Resource
    private TradeDAO tradeDAO;

    @Override
    public void insertOrder(Trade trade) {
        if (null == trade) {
            return;
        }
        tradeDAO.insert(trade);
    }

    @Override
    public Trade selectOrderByOrder(String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return null;
        }
        TradeExample example = new TradeExample();
        example.createCriteria().andOrderNoEqualTo(orderId);
        List<Trade> orders = tradeDAO.selectByExample(example);
        if (CollectionUtils.isEmpty(orders)) {
            return null;
        }
        return orders.get(0);
    }

    @Override
    public void delete(Integer id) {
        if (null == id || id <= 0) {
            return;
        }
        tradeDAO.deleteByPrimaryKey(id);
    }
}

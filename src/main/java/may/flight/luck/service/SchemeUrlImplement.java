package may.flight.luck.service;

import may.flight.luck.dao.SchemeUrlDAO;
import may.flight.luck.entity.SchemeUrl;
import may.flight.luck.entity.SchemeUrlExample;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("schemeService")

public class SchemeUrlImplement implements SchemeUrlService{

    @Resource
    SchemeUrlDAO schemeUrlDAO;

    @Override
    public String selectUrl(String ownerId, int type) {
        if (StringUtils.isBlank(ownerId)) return null;
        SchemeUrlExample example = new SchemeUrlExample();
        example.createCriteria().andOwnerIdEqualTo(ownerId).andTypeEqualTo(type);
        List<SchemeUrl> schemeUrlList = schemeUrlDAO.selectByExample(example);
        if (CollectionUtils.isEmpty(schemeUrlList)) return null;
        return schemeUrlList.get(0).getUrl();
    }

    @Override
    public String selectUrlByPrimaryKey(int key) {
        if (key <= 0) {
            return null;
        }
        SchemeUrl schemeUrl = schemeUrlDAO.selectByPrimaryKey(key);
        if (null == schemeUrl) {
            return null;
        }
        return schemeUrl.getUrl();
    }
}

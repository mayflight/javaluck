package may.flight.luck.component.cache;

import may.flight.luck.component.RedisUtils;
import may.flight.luck.service.data.SchemeUrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SchemeCache {
    private RedisUtils redisUtils;
    private SchemeUrlService schemeUrlService;
    private String attachKey = "scheme_url";

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Autowired
    public void setSchemeUrlService(SchemeUrlService schemeUrlService) {
        this.schemeUrlService = schemeUrlService;
    }

    public String getRedCodeScheme(String ownerId) {
        String key = getCacheKey(ownerId);
        if (StringUtils.isBlank(key)) return null;
        String urlScheme = redisUtils.getStringValue(key);
        if (StringUtils.isNotBlank(urlScheme)) return urlScheme;
        urlScheme = schemeUrlService.selectUrl(ownerId, 1);
        if (StringUtils.isBlank(urlScheme)) return getDefaultRedCode();
        redisUtils.setValue(key, urlScheme, 10, TimeUnit.DAYS);
        return urlScheme;
    }

    private String getDefaultRedCode() {
        String key = getCacheKey("default");
        if (StringUtils.isBlank(key)) return null;
        String urlScheme = redisUtils.getStringValue(key);
        if (StringUtils.isNotBlank(urlScheme)) return urlScheme;
        urlScheme = schemeUrlService.selectUrlByPrimaryKey(1);
        if (StringUtils.isBlank(urlScheme)) return null;
        redisUtils.setValue(key, urlScheme, 10, TimeUnit.DAYS);
        return urlScheme;
    }

    public void setCacheInvalid(String ownerId) {
        String key = getCacheKey(ownerId);
        if (StringUtils.isBlank(key)) return;
        redisUtils.invalid(ownerId);
    }

    private String getCacheKey(String ownerId) {
        if (StringUtils.isBlank(ownerId)) return null;
        return StringUtils.isBlank(ownerId) ? null : attachKey + ownerId;
    }
}

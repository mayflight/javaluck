package may.flight.luck.service;

public interface SchemeUrlService {
    String selectUrl(String ownerId, int type);
    String selectUrlByPrimaryKey(int key);
}

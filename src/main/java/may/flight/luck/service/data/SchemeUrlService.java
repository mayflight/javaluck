package may.flight.luck.service.data;

public interface SchemeUrlService {
    String selectUrl(String ownerId, int type);
    String selectUrlByPrimaryKey(int key);
}

package may.flight.luck.dao;

import may.flight.luck.entity.SchemeUrl;
import may.flight.luck.entity.SchemeUrlExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchemeUrlDAO {
    int countByExample(SchemeUrlExample example);

    int deleteByExample(SchemeUrlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SchemeUrl record);

    int insertSelective(SchemeUrl record);

    List<SchemeUrl> selectByExample(SchemeUrlExample example);

    SchemeUrl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SchemeUrl record, @Param("example") SchemeUrlExample example);

    int updateByExample(@Param("record") SchemeUrl record, @Param("example") SchemeUrlExample example);

    int updateByPrimaryKeySelective(SchemeUrl record);

    int updateByPrimaryKey(SchemeUrl record);
}
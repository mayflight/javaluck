package may.flight.luck.service;


import may.flight.luck.bean.ExcelBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface ExcelService {
    public HSSFWorkbook generateExcel(List<ExcelBean> list);
    public void downloadExcel(HSSFWorkbook workbook, String fileName, HttpServletResponse response);
}

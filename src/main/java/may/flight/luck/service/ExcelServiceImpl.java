package may.flight.luck.service;


import may.flight.luck.bean.ExcelBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;


@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public HSSFWorkbook generateExcel(List<ExcelBean> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        ExcelBean bean = list.get(0);
        if (null == bean) {
            return null;
        }
        String[] codeArray = bean.getColumnCode();
        String[] nameArray = bean.getColumnName();
        if (null == codeArray || null == nameArray
                || codeArray.length != nameArray.length || codeArray.length == 0) {
            return null;
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet");
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(20);
        for (int i = 0; i < nameArray.length; i++) {
            row.createCell(i).setCellValue(nameArray[i]);
            sheet.setColumnWidth(i, 20 * 256);
        }
        try {
            for (int i = 0; i < list.size() ; i++) {
                ExcelBean excelBean = list.get(i);
                if (null == excelBean) {
                    continue;
                }
                HSSFRow detailRow = sheet.createRow(i + 1);
                detailRow.setHeightInPoints(20);
                for (int j = 0; j < codeArray.length; j++) {
                    String code = codeArray[j];
                    Field field = excelBean.getClass().getDeclaredField(code);
                    field.setAccessible(true);
                    Object object = field.get(excelBean);
                    detailRow.createCell(j).setCellValue(object.toString());
                }
            }
        }catch (Exception e) {

        }

        return workbook;
    }

    @Override
    public void downloadExcel(HSSFWorkbook workbook, String fileName, HttpServletResponse response) {
        if (response == null) {
            return;
        }
        if (null == workbook) {
            print(response, "excel 文件生成失败");
            return;
        }
        if (StringUtils.isBlank(fileName)) {
            print(response, "文件名为空");
            return;
        }
        response.setContentType("application/vnd.ms-excel");
        response.reset();
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        try {
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e) {
            print(response, e.getMessage());
        }

    }

    private void print(HttpServletResponse response, String message) {
        if (null == response) {
            return;
        }
        try {
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(message);
        }catch (Exception e) {

        }
    }
}

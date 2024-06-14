package com.yk.Utils;

import lombok.Getter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Template {
    private static Workbook workbook = null;
    @Getter
    private static final String[] titles = new String[]{
            "用户名(只能中文)",
            "手机号(不能重复)",
            "部门(系统中存在的)",
            "聘用形式(正式与非正式)",
            "转正日期(2022/10/24)"};
    public static Workbook getTemplate(){
        if (workbook != null){
            return workbook;
        }
        workbook = new XSSFWorkbook();
        // 创建文本居中样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置背景颜色
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置边框样式
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        // 创建sheet
        Sheet sheet = workbook.createSheet("template");
        // 配置第一行单元格
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        // 设置行高
        row.setHeightInPoints(35);
        cell.setCellValue("人资项目用户批量导入模板(前两行数据为模板设定数据不能修改)");
        cell.setCellStyle(cellStyle);
        // 合并区域
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 4);
        sheet.addMergedRegion(cellAddresses);

        // 配置第二行单元格
        Row row1 = sheet.createRow(1);
        row1.setHeightInPoints(55);
        for(int i = 0; i < titles.length; i++) {
            Cell c = row1.createCell(i);
            c.setCellValue(titles[i]);
            c.setCellStyle(cellStyle);
            sheet.setColumnWidth(i, 30*256);
        }
        return workbook;
    }
}

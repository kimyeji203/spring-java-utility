package com.yjkim.spring.java.utility.excel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * XSSFExcel 관련 유틸리티
 * <p>
 * <p>
 * import
 * - https://mvnrepository.com/artifact/org.apache.poi/poi
 * - https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml
 *
 * @author yjkim
 */
public class XSSFExcelUtil
{
    /**
     * 해당 시트의 {@param rowIx}행 {@param colIx}열 셀의 값
     *
     * @param sheet
     * @param rowIx
     * @param colIx
     * @return
     */
    public static Object getCellValue(XSSFSheet sheet, int rowIx, int colIx)
    {
        if (sheet == null)
        {
            return null;
        }

        XSSFRow row = sheet.getRow(rowIx);
        if (row == null)
        {
            return null;
        }

        XSSFCell cell = row.getCell(colIx);
        return XSSFExcelUtil.getCellValue(cell);
    }

    /**
     * 해당 셀의 값을 문자열로 변환
     *
     * @param cell
     * @return
     */
    public static String getCellStringValue(XSSFCell cell)
    {
        if (cell == null)
        {
            return null;
        }

        switch (cell.getCellType())
        {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
            case BOOLEAN:
                cell.setCellType(CellType.STRING);
                return cell.getStringCellValue();
            case FORMULA:
                return cell.getRawValue();
            case BLANK:
            default:
                return "";
        }
    }

    /**
     * 해당 셀의 값
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(XSSFCell cell)
    {
        if (cell == null)
        {
            return null;
        }

        switch (cell.getCellType())
        {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getRawValue();
            case BLANK:
            default:
                return null;
        }
    }

    /**
     * 해당 시트의 {@param rowIx}행 부터 {@param colIx}열 까지 셀 병합
     *
     * @param sheet
     * @param rowIx
     * @param colIx
     * @return
     */
    public static CellRangeAddress getMergedRegion(XSSFSheet sheet, int rowIx, int colIx)
    {
        for (CellRangeAddress mergedRegion : sheet.getMergedRegions())
        {
            if (mergedRegion.isInRange(rowIx, colIx))
            {
                return mergedRegion;
            }
        }

        return null;
    }

    /**
     * 해당 시트의 병합된 셀 {@param rowIx}행 부터 {@param colIx}열의 값을 문자열로 반환
     *
     * @param sheet
     * @param rowIx
     * @param colIx
     * @return
     */
    public static String getCellStringValueWithMerges(XSSFSheet sheet, int rowIx, int colIx)
    {
        CellRangeAddress mergedRegion = XSSFExcelUtil.getMergedRegion(sheet, rowIx, colIx);

        if (mergedRegion != null)
        {
            XSSFCell cell = sheet.getRow(mergedRegion.getFirstRow()).getCell(mergedRegion.getFirstColumn());
            return XSSFExcelUtil.getCellStringValue(cell);
        }

        return XSSFExcelUtil.getCellStringValue(sheet.getRow(rowIx).getCell(colIx));
    }

}

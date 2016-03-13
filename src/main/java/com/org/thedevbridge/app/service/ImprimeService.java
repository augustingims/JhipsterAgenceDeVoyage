package com.org.thedevbridge.app.service;

import com.org.thedevbridge.app.domain.Imprime;
import com.org.thedevbridge.app.repository.ImprimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
/**
 * Service class for managing users.
 */
@Service
@Transactional
public class ImprimeService {

    private final Logger log = LoggerFactory.getLogger(ImprimeService.class);

    @Inject
    private ImprimeRepository imprimeRepository;


    public void imprimer(Long id) {
        log.debug("Impression De Ticket", id);
            try {
              // créer un classeur
              HSSFWorkbook wb = new HSSFWorkbook();
              Map<String, CellStyle> styles = createStyles(wb);
              // créer une feuille
              HSSFSheet sheet = wb.createSheet();
              sheet.setPrintGridlines(false);
              sheet.setDisplayGridlines(false);

                PrintSetup printSetup = sheet.getPrintSetup();
                printSetup.setLandscape(true);
                sheet.setFitToPage(true);
                sheet.setHorizontallyCenter(true);

                sheet.setColumnWidth(0, 1 * 256);
                sheet.setColumnWidth(1, 3 * 256);
                sheet.setColumnWidth(2, 11 * 256);
                sheet.setColumnWidth(3, 14 * 256);
                sheet.setColumnWidth(4, 14 * 256);
                sheet.setColumnWidth(5, 14 * 256);
                sheet.setColumnWidth(6, 14 * 256);

                Imprime infos = imprimeRepository.findById(id);

                Row titleRow = sheet.createRow(0);
                titleRow.setHeightInPoints(35);
                for (int i = 1; i <= 7; i++) {
                    titleRow.createCell(i).setCellStyle(styles.get("title"));
                }
                Cell titleCell = titleRow.getCell(2);
                titleCell.setCellValue("Agence De Voyage");
                sheet.addMergedRegion(CellRangeAddress.valueOf("$C$1:$H$1"));

                Row row = sheet.createRow(2);
                Cell cell = row.createCell(4);
                cell.setCellValue("Montant Ticket " + infos.getPrix()+" FCFA");
                cell.setCellStyle(styles.get("item_right"));

                row = sheet.createRow(3);
                cell = row.createCell(2);
                cell.setCellValue("Noms & Prenoms");
                cell.setCellStyle(styles.get("item_left"));
                cell = row.createCell(4);
                cell.setCellValue(infos.getClient());
                cell.setCellStyle(styles.get("formula_$"));

                row = sheet.createRow(4);
                cell = row.createCell(2);
                cell.setCellValue("N° Siérge");
                cell.setCellStyle(styles.get("item_left"));
                cell = row.createCell(4);
                cell.setCellValue(infos.getNumero());
                cell.setCellStyle(styles.get("formula_$"));

                row = sheet.createRow(5);
                cell = row.createCell(2);
                cell.setCellValue("Type Ticket");
                cell.setCellStyle(styles.get("item_left"));
                cell = row.createCell(4);
                cell.setCellValue(infos.getNature());
                cell.setCellStyle(styles.get("formula_$"));

                row = sheet.createRow(6);
                cell = row.createCell(2);
                cell.setCellValue("N° Bus");
                cell.setCellStyle(styles.get("item_left"));
                cell = row.createCell(4);
                cell.setCellValue(infos.getNom_bus());
                cell.setCellStyle(styles.get("formula_$"));

                row = sheet.createRow(8);
                cell = row.createCell(2);
                cell.setCellValue("Ville Depart");
                cell.setCellStyle(styles.get("item_left"));
                cell = row.createCell(4);
                cell.setCellValue(infos.getVille_depart());
                cell.setCellStyle(styles.get("formula_$"));

                row = sheet.createRow(9);
                cell = row.createCell(2);
                cell.setCellValue("Ville Arrivée");
                cell.setCellStyle(styles.get("item_left"));
                cell = row.createCell(4);
                cell.setCellValue(infos.getVille_arrivee());
                cell.setCellStyle(styles.get("formula_i"));

                row = sheet.createRow(10);
                cell = row.createCell(2);
                cell.setCellValue("Heure Depart");
                cell.setCellStyle(styles.get("item_left"));
                cell = row.createCell(4);
                cell.setCellValue(infos.getHeure_depart());
                cell.setCellStyle(styles.get("formula_$"));

                row = sheet.createRow(11);
                cell = row.createCell(2);
                cell.setCellValue("Date Voyage");
                cell.setCellStyle(styles.get("item_left"));
                cell = row.createCell(4);
                cell.setCellValue(infos.getDate());
                cell.setCellStyle(styles.get("formula_$"));

              FileOutputStream out = new FileOutputStream("/home/gims/Bureau/ticket-" + id + ".xlsx");
              wb.write(out);
              out.flush();
              out.close();

            }catch (FileNotFoundException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
            }
    }
    private static Map<String, CellStyle> createStyles(HSSFWorkbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)14);
        titleFont.setFontName("Trebuchet MS");
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(titleFont);
        style.setWrapText(true);
        styles.put("title", style);

        Font itemFont = wb.createFont();
        itemFont.setFontHeightInPoints((short)9);
        itemFont.setFontName("Trebuchet MS");
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(itemFont);
        styles.put("item_left", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(itemFont);
        styles.put("item_right", style);


        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(itemFont);
        style.setBorderRight(CellStyle.BORDER_DOTTED);
        style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_DOTTED);
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_DOTTED);
        style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_DOTTED);
        style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_DOTTED);
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("formula_$", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(itemFont);
        style.setBorderRight(CellStyle.BORDER_DOTTED);
        style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_DOTTED);
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_DOTTED);
        style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_DOTTED);
        style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setDataFormat(wb.createDataFormat().getFormat("0"));
        style.setBorderBottom(CellStyle.BORDER_DOTTED);
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("formula_i", style);

        return styles;
    }

}

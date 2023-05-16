package br.mil.ccarj.controle.acesso.services;

import br.mil.ccarj.controle.acesso.models.GrupoPerfil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class RelatorioService {

    @Autowired
    KeycloackService keycloackService;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public byte[] generateFile(String realmName, List<GrupoPerfil> sisplaerGruposPerfis) {
        List<List<UserRepresentation>> groupMembers = new ArrayList<>();
        this.workbook = new XSSFWorkbook();
        buscarUsuariosPorGrupo(realmName, sisplaerGruposPerfis, groupMembers);

        try {
            // cria o header do relatorio.
            for (GrupoPerfil perfil: sisplaerGruposPerfis) {
                writeHeaderLine(perfil.getName());
            }
            // insere os dados nas planilhas.
            for (int i = 0; i < groupMembers.size() ; i++) {
                writeDataLines(groupMembers.get(i), i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            this.workbook.write(out);
            this.workbook.close();
            System.out.println("relatorio gerado com sucesso");
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void buscarUsuariosPorGrupo(String realmName, List<GrupoPerfil> grupoPerfils, List<List<UserRepresentation>> groupMembers) {

        for (GrupoPerfil grupoPerfil : grupoPerfils) {
            if (grupoPerfil.getName().equals("Sisplaer Admin")) {
                groupMembers.add(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Cadastro Basico")) {
                groupMembers.add(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Gerente AO")) {
                groupMembers.add(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Gerente ODS")) {
                groupMembers.add(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Gerente ODS AO 2000")) {
                groupMembers.add(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Gerente ODS UGR")) {
                groupMembers.add(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Gerente PO")) {
                groupMembers.add(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Gerente UGR")) {
                groupMembers.add(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
        }
    }

    private void writeHeaderLine(String groupName) {
        if(this.workbook.getSheet(groupName) == null){
            sheet = this.workbook.createSheet(groupName);
            Row row = sheet.createRow(1);
            CellStyle style = this.workbook.createCellStyle();
            XSSFFont font = this.workbook.createFont();
            font.setBold(true);
            font.setFontHeight(10);
            style.setFont(font);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);

            Row rowGroup = sheet.createRow(0);
            CellStyle rowGroupStyle = this.workbook.createCellStyle();
            rowGroupStyle.setFont(font);
            rowGroupStyle.setAlignment(HorizontalAlignment.CENTER);
            String rowGroupHeader = groupName
                    .toUpperCase(Locale.ROOT)
                    .replace("SISPLAER", "PERFIL");

            createCell(sheet,rowGroup,0, rowGroupHeader, rowGroupStyle);
            createCell(sheet,row, 0, "CPF", style);
            createCell(sheet,row, 1, "OM", style);
            createCell(sheet,row, 2, "NOME", style);
            createCell(sheet,row, 3, "TOTAL", style);
            createCell(sheet,row, 4, "ATUALIZADO EM", style);

            sheet.addMergedRegion(CellRangeAddress.valueOf("A1:E1"));
        }
    }

    private void writeDataLines(List<UserRepresentation> grupos, int index) {
        int rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        XSSFSheet sheetAt = workbook.getSheetAt(index);
        font.setFontHeight(8);
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        for (UserRepresentation user : grupos) {
            Row row = sheetAt.createRow(rowCount++);
            int columnCount = 0;
            createCell(sheetAt, row, columnCount++, user.getUsername(), style);
            createCell(sheetAt, row, columnCount++, user.getLastName(), style);
            createCell(sheetAt, row, columnCount++, user.getFirstName(), style);
        }
        CellReference collumD3 = new CellReference("D3");
        getRow(sheetAt,collumD3.getRow(), collumD3.getCol(), grupos.size(), style);
        CellReference collumE3 = new CellReference("E3");
        getRow(sheetAt,
                collumE3.getRow(),
                collumE3.getCol(),
                LocalDate
                        .now(Clock.systemDefaultZone())
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault()))
                        + " as "+
                        LocalTime
                                .now()
                                .format(DateTimeFormatter.ofPattern("hh:mm:ss")),
                style);
    }

    private void createCell(Sheet sheetAt,Row row, int columnCount, Object value, CellStyle style) {
        sheetAt.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else {
            cell.setCellValue((Integer) value);
        }
        cell.setCellStyle(style);
    }

    private void getRow(Sheet sheetAt, int row, int columnCount, Object value, CellStyle style) {
        sheetAt.autoSizeColumn(columnCount);
        Row rowFound = sheetAt.getRow(row);
        assert rowFound != null;
        Cell rowFoundCell = rowFound.getCell(columnCount, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        assert rowFoundCell != null;
        rowFoundCell.setCellStyle(style);
        if (value instanceof String) {
            rowFoundCell.setCellValue((String) value);
        } else {
            rowFoundCell.setCellValue((Integer) value);
        }
    }
}

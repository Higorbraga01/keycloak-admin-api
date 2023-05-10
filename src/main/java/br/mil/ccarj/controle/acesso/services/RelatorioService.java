package br.mil.ccarj.controle.acesso.services;

import br.mil.ccarj.controle.acesso.models.GrupoPerfil;
import br.mil.ccarj.controle.acesso.models.enums.Perfil;
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
    private List<UserRepresentation> listSispAdmin = new ArrayList<>();
    private List<UserRepresentation> listCadastBas = new ArrayList<>();
    private List<UserRepresentation> listGerentAO = new ArrayList<>();
    private List<UserRepresentation> listGerentODS = new ArrayList<>();
    private List<UserRepresentation> listGerentODSAO2000 = new ArrayList<>();
    private List<UserRepresentation> listGerentODS_UGR = new ArrayList<>();
    private List<UserRepresentation> listGerentPO = new ArrayList<>();
    private List<UserRepresentation> listGerentUGR = new ArrayList<>();
    private List<List<UserRepresentation>> groupMembers = new ArrayList<>();

    public byte[] generateFile(String realmName, List<GrupoPerfil> sisplaerGruposPerfis) {
        this.workbook = new XSSFWorkbook();
        buscarUsuariosPorGrupo(realmName, sisplaerGruposPerfis);

        try {
            int index = 1;
            for (List<UserRepresentation> users : groupMembers) {
                if (!users.isEmpty() && index <= sisplaerGruposPerfis.size()) {
                    Perfil perfil = Perfil.forInt(index);
                    writeHeaderLine(perfil.getNome());
                    writeDataLines(users);
                    index++;
                }
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

    private void buscarUsuariosPorGrupo(String realmName, List<GrupoPerfil> grupoPerfils) {
        for (GrupoPerfil grupoPerfil : grupoPerfils) {
            if (grupoPerfil.getName().equals("Sisplaer Admin")) {
                listSispAdmin.addAll(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Cadastro Basico")) {
                listCadastBas.addAll(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Gerente AO")) {
                listGerentAO.addAll(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Gerente ODS")) {
                listGerentODS.addAll(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Gerente ODS AO 2000")) {
                listGerentODSAO2000.addAll(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Gerente ODS UGR")) {
                listGerentODS_UGR.addAll(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Gerente PO")) {
                listGerentPO.addAll(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
            if (grupoPerfil.getName().equals("Sisplaer Gerente UGR")) {
                listGerentUGR.addAll(keycloackService.findGroupMembers(realmName, grupoPerfil.getId()));
            }
        }
        groupMembers.add(listSispAdmin);
        groupMembers.add(listCadastBas);
        groupMembers.add(listGerentAO);
        groupMembers.add(listGerentODS);
        groupMembers.add(listGerentODSAO2000);
        groupMembers.add(listGerentODS_UGR);
        groupMembers.add(listGerentPO);
        groupMembers.add(listGerentUGR);
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

            createCell(rowGroup,0, rowGroupHeader, rowGroupStyle);
            createCell(row, 0, "CPF", style);
            createCell(row, 1, "OM", style);
            createCell(row, 2, "NOME", style);
            createCell(row, 3, "TOTAL", style);
            createCell(row, 4, "ATUALIZADO EM", style);

            sheet.addMergedRegion(CellRangeAddress.valueOf("A1:E1"));
        }
    }

    private void writeDataLines(List<UserRepresentation> grupos) {
        int rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(8);
        style.setFont(font);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        for (UserRepresentation user : grupos) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, user.getUsername(), style);
            createCell(row, columnCount++, user.getLastName(), style);
            createCell(row, columnCount++, user.getFirstName(), style);
        }
        CellReference collumD3 = new CellReference("D3");
        getRow(collumD3.getRow(), collumD3.getCol(), grupos.size(), style);
        CellReference collumE3 = new CellReference("E3");
        getRow(
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

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else {
            cell.setCellValue((Integer) value);
        }
        cell.setCellStyle(style);
    }

    private void getRow(int row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Row rowFound = sheet.getRow(row);
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

package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.PDF.containsText;
import static java.util.stream.StreamSupport.stream;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckFilesTests {

    private ClassLoader cl = CheckFilesTests.class.getClassLoader();


    public InputStream getFileFromZip(String path, String extension) throws Exception {
        try (InputStream input = cl.getResourceAsStream(path);
             ZipInputStream zis = new ZipInputStream(input)) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                if (fileName.endsWith(extension)) {
                    return new ByteArrayInputStream(zis.readAllBytes());
                }
            }
        }
        return null;
    }

    @Test
    void readPDFTest() throws Exception {
        try (InputStream input = getFileFromZip("data.zip", ".pdf")) {
            PDF pdf = new PDF(input);
            assertThat(pdf, containsText("This is a simple PDF file. Fun fun fun"));
        }
    }

    @Test
    void readExcelTest() throws Exception {
        try (InputStream is = getFileFromZip("data.zip", ".xlsx")) {
            XLS xls = new XLS(is.readAllBytes());

            boolean foundId = stream(xls.excel.spliterator(), false)
                    .flatMap(sheet -> stream(sheet.spliterator(), false))
                    .flatMap(row -> stream(row.spliterator(), false))
                    .anyMatch(cell -> {
                        if (cell.getCellType() == NUMERIC) {
                            return cell.getNumericCellValue() == 1562.0;
                        } else if (cell.getCellType() == STRING) {
                            try {
                                return Double.parseDouble(cell.getStringCellValue()) == 1562.0;
                            } catch (NumberFormatException ignored) {
                                return false;
                            }
                        }
                        return false;
                    });

            assertTrue(foundId, "ID 1562 не найден в Excel файле");
        }
    }

    @Test
    void readCSVTest() throws Exception {
        try (InputStream is = getFileFromZip("data.zip", ".csv");
             InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(reader)) {

            boolean found = csvReader.readAll().stream()
                    .flatMap(Stream::of)
                    .anyMatch(cell -> cell.contains("smith79"));

            assertTrue(found, "Значение 'smith79' не найдено в CSV файле");
        }
    }
}
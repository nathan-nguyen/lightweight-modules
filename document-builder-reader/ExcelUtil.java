import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelUtil {
	public static void main(String[] args) throws IOException {
		ExcelUtil util = new ExcelUtil();
		util.readInput();
		util.writeOutput();
	}

	private void readInput() throws IOException {
		FileInputStream file = new FileInputStream(new File("Input.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				System.out.print(cell + "\t");
			}
			System.out.println();
		}
		file.close();
	}

	private void writeOutput() throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();

		Row row = sheet.getRow(10);
		if (row == null) row = sheet.createRow(10);

		Cell cell = row.getCell(10);
		if (cell == null) cell = row.createCell(10);
		cell.setCellValue("Hello World!");

		FileOutputStream outFile = new FileOutputStream(new File("Output.xlsx"));
		workbook.write(outFile);
		outFile.close();
	}
}

package topBloc.quickstart;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class App {

	public static void main(String[] args) throws IOException {
		try {

			String str1 = "";
			String str2 = "computer science";
			String str3 = "";
			String str4 = "";
			String female = "F";
			String saveArray = "";

			double idNum = 0;
			double totTest = 0;
			double avgTest = 0;

			int rowCnt = 0, rowCnt2 = 0, rowCnt3 = 0; // setup row counters
			int colCnt = 0, colCnt2 = 0, colCnt3 = 0; // setup column counters

			String[][] myArray = new String[100][100];
			double[][] numArray = new double[100][100];
			double[][] testArray = new double[100][100];
			double[][] retArray = new double[100][100];

			File excel = new File("C:\\Users\\Ryan\\Documents\\TopBloc\\StudentInfo.xlsx");
			File excel2 = new File("C:\\Users\\Ryan\\Documents\\TopBloc\\TestScores.xlsx");
			File excel3 = new File("C:\\Users\\Ryan\\Documents\\TopBloc\\TestRetakeScores.xlsx");

			FileInputStream fis = new FileInputStream(excel);
			FileInputStream fis2 = new FileInputStream(excel2);
			FileInputStream fis3 = new FileInputStream(excel3);

			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFWorkbook book2 = new XSSFWorkbook(fis2);
			XSSFWorkbook book3 = new XSSFWorkbook(fis3);

			XSSFSheet sheet = book.getSheetAt(0);
			XSSFSheet sheet2 = book2.getSheetAt(0);
			XSSFSheet sheet3 = book3.getSheetAt(0);

			Iterator<Row> itr = sheet.iterator();
			Iterator<Row> itr2 = sheet2.iterator();
			Iterator<Row> itr3 = sheet3.iterator();

			// Iterating over of StudentInfo Excel file
			while (itr.hasNext()) {
				Row row = itr.next();
				rowCnt++; // iterate to determine amount of rows
				colCnt = 0; // reset column count

				// Iterating over each column of StudentInfo Excel file
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						str1 = cell.getStringCellValue();
						myArray[rowCnt][colCnt] = str1;
						break;
					case Cell.CELL_TYPE_NUMERIC:
						myArray[rowCnt][colCnt] = Double.toString(cell.getNumericCellValue());
						numArray[rowCnt][colCnt] = cell.getNumericCellValue();
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						break;
					default:
					}
					colCnt++; // iterate to determine amount of columns
				}
			}

			// Sort through the array based off student's ID numbers
			for (int j = 2; j <= rowCnt; j++) {

				for (int q = rowCnt; q > 0; q--) {
					//Test if student's ID number is less than the student after
					if (numArray[j][0] > numArray[q][0] && j < rowCnt && q > 0) {
						for (int i = 0; i <= colCnt; i++) {
							String tmpVal = myArray[j][i];	
							myArray[j][i] = myArray[q][i];	
							myArray[q][i] = tmpVal;	

							double tmpVal2 = numArray[j][i];
							numArray[j][i] = numArray[q][i];
							numArray[q][i] = tmpVal2;
						}
					}
				}
			}

			// Iterating over TestScores Excel file
			while (itr2.hasNext()) {
				Row row2 = itr2.next();

				rowCnt2++; // iterate to determine amount of rows
				colCnt2 = 0; // reset column count

				// Iterating over each column of Excel file
				Iterator<Cell> cellIterator2 = row2.cellIterator();

				while (cellIterator2.hasNext()) {
					Cell cell2 = cellIterator2.next();

					switch (cell2.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						break;
					case Cell.CELL_TYPE_NUMERIC:
						testArray[rowCnt2][colCnt2] = cell2.getNumericCellValue();
						break;
					default:
					}
					colCnt2++; // iterate to determine amount of columns
				}
			}

			// Iterating over RetakeScores Excel file
			while (itr3.hasNext()) {
				Row row3 = itr3.next();

				rowCnt3++; // iterate to determine amount of rows
				colCnt3 = 0; // reset column count

				// Iterating over each column of RetakeScores Excel file
				Iterator<Cell> cellIterator3 = row3.cellIterator();
				while (cellIterator3.hasNext()) {
					Cell cell3 = cellIterator3.next();
					switch (cell3.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						break;
					case Cell.CELL_TYPE_NUMERIC:
						retArray[rowCnt3][colCnt3] = cell3.getNumericCellValue(); // place retake scores into an array
						break;
					default:
					}
					colCnt3++; // iterate to determine amount of columns
				}
			}

			for (int i = 1; i <= rowCnt2; i++) {
				for (int j = 1; j <= rowCnt3; j++) {
					if (testArray[i][0] == retArray[j][0] && testArray[i][1] < retArray[j][1]) {
						testArray[i][1] = retArray[j][1];
					}
				}
				totTest = testArray[i][1] + totTest;
			}
			avgTest = Math.round(totTest / 9);

			ByteArrayOutputStream stream = new ByteArrayOutputStream();

			
/*
* Note: http://3.86.140.38:5000/challenge was not working. Whenever I searched
* for it error code https 400 appeared. I discovered a website that tests Java
* post, which was used as a proof of concept in order to show the code
* sucessfully posts a request.
*/
			URL url = new URL("https://httpbin.org/post");

			String urlParameters = new String("id:ryan.guerrero@valpo.edu,name:Ryan Guerrero,");
			String urlParam2 = ("avgTest:" + Double.toString(avgTest));
			String urlParam3 = "";
			String finalID = "";

			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			byte[] postData2 = urlParam2.getBytes(StandardCharsets.UTF_8);
			byte[] postData3 = urlParam3.getBytes(StandardCharsets.UTF_8);

			// Sort array of female students who are majoring in computer science
			for (int j = 1; j < rowCnt; j++) {
				for (int i = 0; i < colCnt; i++) {
					if (myArray[j][i].equals(str2) && myArray[j][i + 1].equals(female)) {
						String space = ",femID:";
						stream.write(space.getBytes());
						stream.write(myArray[j][i - 1].getBytes());
						finalID = new String(stream.toByteArray());
						postData3 = finalID.getBytes(StandardCharsets.UTF_8);
					}
				}
			}

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
				wr.write(postData);
				wr.write(postData2);
				wr.write(postData3);
			}

			StringBuilder content;

			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

				String line;
				content = new StringBuilder();

				while ((line = in.readLine()) != null) {
					content.append(line);
					content.append(System.lineSeparator());
				}
			}

			System.out.println(content.toString());

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

	}

}

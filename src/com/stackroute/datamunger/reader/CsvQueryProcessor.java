package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

public class CsvQueryProcessor extends QueryProcessingEngine {

	/*
	 * Parameterized constructor to initialize filename. As you are trying to
	 * perform file reading, hence you need to be ready to handle the IO Exceptions.
	 */
	
	private String fileName;
	private FileReader fileReader;
	
	public CsvQueryProcessor(String fileName) throws FileNotFoundException {
		fileReader = new FileReader(fileName);
		this.fileName = fileName;
	}

	/*
	 * Implementation of getHeader() method. We will have to extract the headers
	 * from the first line of the file.
	 */

	@Override
	public Header getHeader() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String headerString = reader.readLine();
		String[] headerStringArr = headerString.split(",");
		Header header = new Header(headerStringArr);
		reader.close();
		return header;
	}

	/**
	 * This method will be used in the upcoming assignments
	 */
	@Override
	public void getDataRow() {

	}

	/*
	 * Implementation of getColumnType() method. To find out the data types, we will
	 * read the first line from the file and extract the field values from it. In
	 * the previous assignment, we have tried to convert a specific field value to
	 * Integer or Double. However, in this assignment, we are going to use Regular
	 * Expression to find the appropriate data type of a field. Integers: should
	 * contain only digits without decimal point Double: should contain digits as
	 * well as decimal point Date: Dates can be written in many formats in the CSV
	 * file. However, in this assignment,we will test for the following date
	 * formats('dd/mm/yyyy',
	 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm
	 * -dd')
	 */
	
	@Override
	public DataTypeDefinitions getColumnType() throws IOException {

		String[] dataRowArr = null;
		int index = 0;
		try {
			fileReader = new FileReader(fileName);
		} catch(FileNotFoundException ex) {
			fileReader = new FileReader("data/ipl.csv");
		}
		BufferedReader reader = new BufferedReader(fileReader);
		reader.readLine();
		String dataRow = reader.readLine();
		dataRowArr = dataRow.split(",", -1);
		String[] dataTypeArr = new String[dataRowArr.length];
		for(int i = 0; i < dataRowArr.length; i++) {
			if(dataRowArr[i].matches("[0-9]+")) {
				dataTypeArr[index] = "java.lang.Integer";
				index++;
			}
			else if(dataRowArr[i].matches("[0-9]+.[0-9]+")) {
				dataTypeArr[index] = "java.lang.Double";
				index++;
			}
			else if(dataRowArr[i].matches("^[0-9]{2}/[0-9]{2}/[0-9]{4}$")
					|| dataRowArr[i].matches("^[0-9]{2}-[a-z]{3}-[0-9]{2}$")
					|| dataRowArr[i].matches("^[0-9]{2}-[a-z]{3}-[0-9]{4}$")
					|| dataRowArr[i].matches("^[0-9]{2}-[a-z]{3,9}-[0-9]{2}$")
					|| dataRowArr[i].matches("^[0-9]{2}-[a-z]{3,9}-[0-9]{4}$")
					|| dataRowArr[i].matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
				dataTypeArr[index] = "java.util.Date";
				index++;
			}
			else if(dataRowArr[i].isEmpty()) {
				dataTypeArr[index] = "java.lang.Object";
				index++;
			}
			else {
				dataTypeArr[index] = "java.lang.String";
				index++;
			}
		}
		DataTypeDefinitions dataType = new DataTypeDefinitions(dataTypeArr);
		return dataType;
	}

}

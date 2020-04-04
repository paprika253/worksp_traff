package geoload;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class OpenCSVReadEx
{
	public static void main(String[] args) throws IOException, CsvValidationException
	{
		String fileName = "/home/yaa/GeoLite2-City-CSV_20200310/GeoLite2-City-Blocks-IPv4.csv";
		try (FileInputStream fis = new FileInputStream(fileName);
				InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
				CSVReader reader = new CSVReader(isr))
		{
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null)
			{
				for (String e : nextLine)
				{
					System.out.format("%s ", e);
				}
			}
		}
	}
}
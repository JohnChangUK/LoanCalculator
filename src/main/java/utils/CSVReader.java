package utils;

import model.Lender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CSVReader {

    public static List<Lender> loadDataFromCSVFile(String csvFilePath) throws IOException {
        File file = new File(csvFilePath);
        InputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<Lender> allLenders = reader.lines().skip(1).map(CSVReader::returnAllLenders).collect(Collectors.toList());
        reader.close();

        return allLenders;
    }

    private static Lender returnAllLenders(String line) {
        String[] csvData = line.split(",");
        String lender = csvData[0];
        BigDecimal rate = new BigDecimal(csvData[1]);
        Integer available = Integer.valueOf(csvData[2]);

        return new Lender(lender, rate, available);
    }
}

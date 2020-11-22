package au.com.polonious.integration.service.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class CsvHelper {
    public static String CSV_FORMAT = "text/csv";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:m");

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!CSV_FORMAT.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public List<ReferralRecord> csvToTable(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT
//                             .withFirstRecordAsHeader().withIgnoreHeaderCase()
                             .withTrim());) {

            List<List<String>> table = new ArrayList<List<String>>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Stream<String> targetStream = StreamSupport.stream(csvRecord.spliterator(), false);
                List<String> singleRow = targetStream.collect(Collectors.toList());
                table.add(singleRow);
            }
            return table.stream().map(c -> this.getReferralRecord(c)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private ReferralRecord getReferralRecord(List<String> list) {
        int i =0;
        LocalDateTime localDateTime = LocalDateTime.parse(list.get(4), formatter);

        return ReferralRecord.builder().exposireId(list.get(i++))
                .detectionMethod(list.get(i++))
                .detectionMethod2(list.get(i++))
                .score(Float.valueOf(list.get(i++)))
                .timestamp(LocalDateTime.parse(list.get(4), formatter))
                .build();
    }

}

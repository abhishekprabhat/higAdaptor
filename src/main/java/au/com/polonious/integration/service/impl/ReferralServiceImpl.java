package au.com.polonious.integration.service.impl;

import au.com.polonious.integration.service.ReferralService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
public class ReferralServiceImpl implements ReferralService{
    @Autowired
    CsvHelper csvHelper;

    @Override
    public List<ReferralRecord> prepareReferrals(MultipartFile file) {
        log.info(String.format("Has csv format? %s", CsvHelper.hasCSVFormat(file)));
        try{
            List<ReferralRecord> rawTables = csvHelper.csvToTable(file.getInputStream());
            rawTables = rawTables.stream().filter(c -> c.getScore() >= 70).collect(Collectors.toList());
            return rawTables;

        }catch (IOException e){
            log.info(String.format("Error Reading file: %s\n%s", file.getName(), e.getMessage()));
        }

        return null;
    }
}

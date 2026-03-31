package guru.springframework.spring_7_rest_mvc.services;


/*
Created by Zsolt Melich (BT - IVR team)
*/

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import guru.springframework.spring_7_rest_mvc.entities.Beer;
import guru.springframework.spring_7_rest_mvc.model.BeerCSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class BeerCsvServiceImpl implements BeerCsvService {

    @Override
    public List<BeerCSVRecord> convertCSV(File csvFile) {

        try {
            List<BeerCSVRecord> beerCSVRecords = new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile))
                    .withType(BeerCSVRecord.class)
                    .build().parse();

            return beerCSVRecords;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

package guru.springframework.spring_7_rest_mvc.services;

import guru.springframework.spring_7_rest_mvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {

    List<BeerCSVRecord> convertCSV(File csvFile);

}

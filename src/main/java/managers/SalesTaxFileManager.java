package managers;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ernestholloway
 * <p>
 * A class used to read all of the .csv files that represent the sales tax info for all 52 states within the U.S.A.
 */
@Singleton
public class SalesTaxFileManager {
    private static final String CSV_ROW_HEADER = "State,ZipCode,TaxRegionName,StateRate,EstimatedCombinedRate,EstimatedCountyRate,EstimatedCityRate,EstimatedSpecialRate,RiskLevel";
    private static final String TAX_FILES_DIRECTORY = "TAXRATES";

    /**
     * @return the full list of file names for the given directory resources directory.
     */
    public String[] getTaxFileNames() {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource(TAX_FILES_DIRECTORY).getPath();

        File filePath = new File(path);

        File[] listOfFiles = filePath.listFiles();

        if (listOfFiles == null || listOfFiles.length < 1) {
            return null;
        } else {
            String[] fileNames = new String[listOfFiles.length];

            for (int i = 0; i < listOfFiles.length; i++) {
                //As an extra precaution only return tax file names that are of type .csv
                if (listOfFiles[i].getName().contains(".csv")) {
                    fileNames[i] = path + "/" + listOfFiles[i].getName();
                }

            }

            return fileNames;
        }
    }

    /**
     * This will generate a list of String values representing each row of .csv data for a corresponding tax file.
     *
     * @param fileNameAndPath is the full file name and path for .csv file containing the tax data.
     * @return a {@link List} of string objects if .csv data is found, null otherwise.
     */
    public List<String> getSalesTaxData(final String fileNameAndPath) {
        try {
            List<String> salesTaxData = new ArrayList<>();

            Stream<String> stream = Files.lines(Paths.get(fileNameAndPath));

            salesTaxData = stream
                    .filter(readLine -> !readLine.startsWith(CSV_ROW_HEADER))
                    .collect(Collectors.toList());

            stream.close();

            return salesTaxData;

        } catch (IOException e) {
            return null;
        }
    }
}

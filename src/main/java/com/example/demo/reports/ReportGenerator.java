package com.example.demo.reports;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public class ReportGenerator {

    public void generateTextFile(List<InhousePart> inhouseParts, List<OutsourcedPart> outsourcedParts, List<Product> products) {
        try (FileWriter fileWriter = new FileWriter("Report.txt")) {
            // Add date and time at the very top
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            fileWriter.write("Report generated on: " + currentTime.format(formatter) + "\n\n");

            writeParts(fileWriter, "Inhouse Parts", inhouseParts);
            writeParts(fileWriter, "Outsourced Parts", outsourcedParts);
            writeProducts(fileWriter, products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeParts(FileWriter fileWriter, String title, List<? extends Part> parts) throws IOException {
        fileWriter.write(title + "\n");

        // Calculate maximum lengths for each column
        int maxNameLength = "Name".length();
        int maxStoreNumberLength = "Store".length();
        int maxIdLength = "ID".length();
        int maxPriceLength = "Price".length();
        int maxInvLength = "Inv".length();
        int maxDateLength = "Date Added".length();
        int maxCompanyNameLength = "Company Name".length();

        for (Part part : parts) {
            maxNameLength = Math.max(maxNameLength, part.getName().length());
            maxStoreNumberLength = Math.max(maxStoreNumberLength, String.valueOf(part.getStoreNumber()).length());
            maxIdLength = Math.max(maxIdLength, String.valueOf(part.getId()).length());
            maxPriceLength = Math.max(maxPriceLength, String.valueOf(part.getPrice()).length());
            maxInvLength = Math.max(maxInvLength, String.valueOf(part.getInv()).length());
            maxDateLength = Math.max(maxDateLength, part.getDate().length());
            if (part instanceof OutsourcedPart) {
                OutsourcedPart outsourcedPart = (OutsourcedPart) part;
                maxCompanyNameLength = Math.max(maxCompanyNameLength, outsourcedPart.getCompanyName().length());
            }
        }

        // Add a little padding to the max lengths
        maxNameLength += 2;
        maxStoreNumberLength += 2;
        maxIdLength += 2;
        maxPriceLength += 2;
        maxInvLength += 2;
        maxDateLength += 2;
        maxCompanyNameLength += 2;

        // Write headers for outsourcedParts
        fileWriter.write(
                String.format("%-" + maxNameLength + "s", "Name") +
                        String.format("%-" + maxStoreNumberLength + "s", "Store") +
                        String.format("%-" + maxIdLength + "s", "ID") +
                        String.format("%-" + maxPriceLength + "s", "Price") +
                        String.format("%-" + maxInvLength + "s", "Inv") +
                        String.format("%-" + maxDateLength + "s", "Date Added") +
                        (title.equals("Outsourced Parts") ? String.format("%-" + maxCompanyNameLength + "s", "Company Name") : "") +
                        "\n"
        );

        // Write OutsourcedParts
        for (Part part : parts) {
            fileWriter.write(
                    String.format("%-" + maxNameLength + "s", part.getName()) +
                            String.format("%-" + maxStoreNumberLength + "s", part.getStoreNumber()) +
                            String.format("%-" + maxIdLength + "s", part.getId()) +
                            String.format("%-" + maxPriceLength + "s", part.getPrice()) +
                            String.format("%-" + maxInvLength + "s", part.getInv()) +
                            String.format("%-" + maxDateLength + "s", part.getDate()) +
                            (part instanceof OutsourcedPart ?
                                    String.format("%-" + maxCompanyNameLength + "s", ((OutsourcedPart) part).getCompanyName()) : "") +
                            "\n"
            );
        }

        fileWriter.write("\n");
    }

    private void writeProducts(FileWriter fileWriter, List<? extends Product> products) throws IOException {
        fileWriter.write("Products" + "\n");

        // Calculate maximum lengths for each column
        int maxNameLength = "Name".length();
        int maxStoreNumberLength = "Store".length();
        int maxIdLength = "ID".length();
        int maxPriceLength = "Price".length();
        int maxInvLength = "Inv".length();
        int maxDateLength = "Date Added".length();
        int maxAssociatedPartsLength = "Associated Parts".length();

        for (Product product : products) {
            maxNameLength = Math.max(maxNameLength, product.getName().length());
            maxStoreNumberLength = Math.max(maxStoreNumberLength, String.valueOf(product.getStoreNumber()).length());
            maxIdLength = Math.max(maxIdLength, String.valueOf(product.getId()).length());
            maxPriceLength = Math.max(maxPriceLength, String.valueOf(product.getPrice()).length());
            maxInvLength = Math.max(maxInvLength, String.valueOf(product.getInv()).length());
            maxDateLength = Math.max(maxDateLength, product.getDate().length());

            Set<? extends Part> associatedParts = product.getAssociatedParts();
            if (associatedParts != null && !associatedParts.isEmpty()) {
                StringBuilder partsNames = new StringBuilder();
                for (Part part : associatedParts) {
                    partsNames.append(part.getName()).append(", ");
                }
                maxAssociatedPartsLength = Math.max(maxAssociatedPartsLength, partsNames.length());
            }
        }

        // Add a little padding to the max lengths
        maxNameLength += 2;
        maxStoreNumberLength += 2;
        maxIdLength += 2;
        maxPriceLength += 2;
        maxInvLength += 2;
        maxDateLength += 2;
        maxAssociatedPartsLength += 2;

        // Write headers
        fileWriter.write(
                String.format("%-" + maxNameLength + "s", "Name") +
                        String.format("%-" + maxStoreNumberLength + "s", "Store") +
                        String.format("%-" + maxIdLength + "s", "ID") +
                        String.format("%-" + maxPriceLength + "s", "Price") +
                        String.format("%-" + maxInvLength + "s", "Inv") +
                        String.format("%-" + maxDateLength + "s", "Date Added") +
                        String.format("%-" + maxAssociatedPartsLength + "s", "Associated Parts") + "\n"
        );

        // Write products
        for (Product product : products) {
            fileWriter.write(
                    String.format("%-" + maxNameLength + "s", product.getName()) +
                            String.format("%-" + maxStoreNumberLength + "s", product.getStoreNumber()) +
                            String.format("%-" + maxIdLength + "s", product.getId()) +
                            String.format("%-" + maxPriceLength + "s", product.getPrice()) +
                            String.format("%-" + maxInvLength + "s", product.getInv()) +
                            String.format("%-" + maxDateLength + "s", product.getDate())
            );

            Set<? extends Part> associatedParts = product.getAssociatedParts();
            if (associatedParts != null && !associatedParts.isEmpty()) {
                StringBuilder partsNames = new StringBuilder();
                for (Part part : associatedParts) {
                    partsNames.append(part.getName()).append(", ");
                }
                fileWriter.write(String.format("%-" + maxAssociatedPartsLength + "s", partsNames.toString()));
            }

            fileWriter.write("\n");
        }

        fileWriter.write("\n");
    }
}

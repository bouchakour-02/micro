package com.example.microservice.service;

import com.example.microservice.model.InsurancePreference;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfExportService {

    public byte[] generatePreferencesPdf(List<InsurancePreference> preferences) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            
            document.open();
            
            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Insurance Preferences Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);
            
            // Create table
            PdfPTable table = new PdfPTable(6); // Number of columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            
            // Set column widths
            float[] columnWidths = {1f, 2f, 1f, 1.5f, 2f, 2f};
            table.setWidths(columnWidths);
            
            // Add table headers
            addTableHeader(table);
            
            // Add data rows
            for (InsurancePreference preference : preferences) {
                addTableRow(table, preference);
            }
            
            document.add(table);
            
            // Add footer
            Paragraph footer = new Paragraph("Report generated on " + java.time.LocalDate.now(), 
                    FontFactory.getFont(FontFactory.HELVETICA, 10));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            document.add(footer);
            
            document.close();
            
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
    
    private void addTableHeader(PdfPTable table) {
        String[] headers = {"ID", "User Email", "Age", "Income", "Interests", "Details"};
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        
        for (String header : headers) {
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPadding(5);
            cell.setPhrase(new Phrase(header, headerFont));
            table.addCell(cell);
        }
    }
    
    private void addTableRow(PdfPTable table, InsurancePreference preference) {
        // ID
        table.addCell(preference.getId().toString());
        
        // User Email
        table.addCell(preference.getUserEmail() != null ? preference.getUserEmail() : "N/A");
        
        // Age
        table.addCell(preference.getAge() != null ? preference.getAge().toString() : "N/A");
        
        // Income
        table.addCell(preference.getAnnualIncome() != null ? "$" + preference.getAnnualIncome() : "N/A");
        
        // Insurance Interests
        StringBuilder interests = new StringBuilder();
        if (Boolean.TRUE.equals(preference.getInterestedInLifeInsurance())) interests.append("Life, ");
        if (Boolean.TRUE.equals(preference.getInterestedInHealthInsurance())) interests.append("Health, ");
        if (Boolean.TRUE.equals(preference.getInterestedInVehicleInsurance())) interests.append("Vehicle, ");
        if (Boolean.TRUE.equals(preference.getInterestedInPropertyInsurance())) interests.append("Property");
        
        // Remove trailing comma if exists
        String interestsStr = interests.toString();
        if (interestsStr.endsWith(", ")) {
            interestsStr = interestsStr.substring(0, interestsStr.length() - 2);
        }
        table.addCell(interestsStr.isEmpty() ? "None" : interestsStr);
        
        // Additional Details
        StringBuilder details = new StringBuilder();
        if (Boolean.TRUE.equals(preference.getHasVehicle())) 
            details.append("Vehicle: ").append(preference.getVehicleType()).append("\n");
        if (Boolean.TRUE.equals(preference.getHasProperty())) 
            details.append("Property: ").append(preference.getPropertyType()).append("\n");
        if (Boolean.TRUE.equals(preference.getHasFamilyDependents())) 
            details.append("Has Dependents\n");
        if (Boolean.TRUE.equals(preference.getHasHealthIssues())) 
            details.append("Has Health Issues\n");
        
        table.addCell(details.toString().isEmpty() ? "No additional details" : details.toString());
    }
} 
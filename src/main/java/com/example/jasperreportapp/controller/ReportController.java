package com.example.jasperreportapp.controller;

import com.example.jasperreportapp.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/actors/pdf")
    public ResponseEntity<byte[]> generateActorsReportPdf() {
        try {
            byte[] pdfBytes = reportService.generateActorReportPdf();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "actors_report.pdf");
            headers.setContentLength(pdfBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (JRException e) {
            return ResponseEntity.status(500).body(("Error al generar el reporte: " + e.getMessage()).getBytes());
        }
    }
}
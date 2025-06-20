package com.utn.frp.p3.jasperreportapp.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.utn.frp.p3.jasperreportapp.model.Actor;
import com.utn.frp.p3.jasperreportapp.repository.ActorRepository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReportController {

    private final ActorRepository actorRepository;

    public ReportController(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

 

    @GetMapping("/generate-report")
    public ResponseEntity<byte[]> generateReport() throws Exception {
        List<Actor> actors = actorRepository.findAll();

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(actors);

        InputStream jasperStream = new ClassPathResource("reports/actor_report.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CreatedBy", "Spring Boot App");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "actor_report.pdf");

        return ResponseEntity.ok().headers(headers).body(reportBytes);
    }
}
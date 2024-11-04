package com.bank.controller;

import com.bank.service.ReportService;

public class ReportController {
    private final ReportService reportService;

    public ReportController() {
        this.reportService = new ReportService();
    }

    public String generateReport(String reportType) {
        return reportService.generateReport(reportType);
    }
}
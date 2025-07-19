package utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static String reportPath;


    public static ExtentReports getInstance() {
        if (extent == null) {
            String timeStamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
            String reportName = timeStamp + "_CartBridgeAutomationReport.html";
            reportPath = "reports/" + reportName; // Put inside reports folder
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // 🔽 Add project details
            extent.setSystemInfo("Project Name", "CartBridge API Automation");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Ava");
            extent.setSystemInfo("Execution Time", java.time.LocalDateTime.now().toString());
        }
        return extent;
    }

    public static void createTest(String testName, String description) {
        test = getInstance().createTest(testName, description);
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
        try {
            File htmlFile = new File(reportPath).getAbsoluteFile();
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            System.out.println("Could not open report in browser: " + e.getMessage());
        }

    }
}

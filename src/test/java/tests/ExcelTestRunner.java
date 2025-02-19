package tests;

import org.apache.poi.ss.usermodel.*;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ExcelTestRunner {
    public static void main(String[] args) throws ClassNotFoundException {
        // Read the class names and execution flag from the Excel sheet
        List<String> classNames = ExcelUtils.getClassNamesFromExcelSheet();
        List<String> executionFlags = ExcelUtils.getExecutionFlagsFromExcelSheet();

        // Create TestNG object
        TestNG testng = new TestNG();

        // Create a TestListenerAdapter to listen to test results
        TestListenerAdapter tla = new TestListenerAdapter();

        // Add the TestListenerAdapter to TestNG
        testng.addListener(tla);

        // Create a new test suite
        XmlSuite suite = new XmlSuite();
        suite.setName("Dynamic Test Suite");
       // suite.setParallel();

        // Create a new test with the specified class names
        XmlTest test = new XmlTest(suite);
        test.setName("Dynamic Test");
        test.setXmlClasses(getXmlClasses(classNames, executionFlags));

        // Add the test to the suite
        suite.setTests(new ArrayList<XmlTest>() {{
            add(test);
        }});

        // Create a list of suites to run
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);

        // Set the suites to run on TestNG
        testng.setXmlSuites(suites);

        // Run the tests
        testng.run();

        // Get the results
        List<ITestResult> passedTests = tla.getPassedTests();
        List<ITestResult> failedTests = tla.getFailedTests();
        List<ITestResult> skippedTests = tla.getSkippedTests();

        // Print the results
        System.out.println("Passed Tests:");
        for (ITestResult result : passedTests) {
            System.out.println(result.getName() + " passed");
        }

        System.out.println("\nFailed Tests:");
        for (ITestResult result : failedTests) {
            System.out.println(result.getName() + " failed");
        }
        System.out.println("\nSkipped Tests:");
        for (ITestResult result : skippedTests) {
            System.out.println(result.getName() + " skipped");
        }
    }

    /**
     * Get a list of XmlClasses for the specified class names.
     * Only include classes that have execution flag set to "Yes".
     */
    private static List<XmlClass> getXmlClasses(List<String> classNames, List<String> executionFlags) throws ClassNotFoundException {
        List<XmlClass> xmlClasses = new ArrayList<>();
        for (int i = 0; i < classNames.size(); i++) {
            String className = classNames.get(i);
            String executionFlag = executionFlags.get(i);
            if (executionFlag.equalsIgnoreCase("Yes")) {
                XmlClass xmlClass = new XmlClass(className);
                List<XmlInclude> xmlIncludes = new ArrayList<>();
                Method[] methods = Class.forName(className).getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Test.class)) {
                        Test annotation = method.getAnnotation(Test.class);
                        if (annotation != null) {
                            int priority = annotation.priority();
                            XmlInclude xmlInclude = new XmlInclude(method.getName(), priority);
                            xmlIncludes.add(xmlInclude);
                        }
                    }
                }
                xmlClass.setIncludedMethods(xmlIncludes);
                xmlClasses.add(xmlClass);
            }
        }
        return xmlClasses;
    }
}


class ExcelUtils {
    private static final String EXCEL_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/TestData/ExecutionFlagsMultiTests.xlsx";

    public static List<String> getClassNamesFromExcelSheet() {
        List<String> classNames = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(EXCEL_FILE_PATH)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    classNames.add(cell.getStringCellValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classNames;
    }
    public static List<String> getExecutionFlagsFromExcelSheet() {
        List<String> executionFlags = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(EXCEL_FILE_PATH)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(1);
                if (cell != null) {
                    String cellValue = cell.getStringCellValue().toLowerCase();
                    if (cellValue.equals("yes") || cellValue.equals("y")) {
                        executionFlags.add("Yes");
                    } else {
                        executionFlags.add("No");
                    }
                } else {
                    executionFlags.add("No");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return executionFlags;
    }
}






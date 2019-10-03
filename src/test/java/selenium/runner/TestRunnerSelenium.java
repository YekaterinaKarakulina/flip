package selenium.runner;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import selenium.reporting.AllureListener;
import selenium.reporting.SimpleTestListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRunnerSelenium {

    public static void main(String[] args) {
        SimpleTestListener testListener = new SimpleTestListener();
        AllureListener allureListener = new AllureListener();
        ReportPortalTestNGListener listener = new ReportPortalTestNGListener();
        TestNG tng = new TestNG();
        tng.addListener(testListener);
        tng.addListener(allureListener);
        tng.addListener((Object) listener);
        XmlSuite suite = new XmlSuite();
        suite.setName("suite");
        List<String> files = Arrays.asList("src/test/resources/suites/suite.xml");
        suite.setSuiteFiles(files);
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        tng.setXmlSuites(suites);
        tng.run();
    }

}

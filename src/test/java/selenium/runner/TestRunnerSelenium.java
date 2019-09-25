package selenium.runner;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRunnerSelenium {

    public static void main(String[] args) {
        TestNG tng = new TestNG();
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

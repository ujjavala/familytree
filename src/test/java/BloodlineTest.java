import com.family.tree.Bloodline;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import static com.family.tree.Bloodline.main;

@Suite
@ExtendWith(MockitoExtension.class)
public class BloodlineTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    CrashAndBurn instance = new CrashAndBurn();

    @After
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testMain() throws IOException {
        String[] args = {getFilePath()};
        main(args);
        Assertions.assertEquals("PERSON_NOT_FOUND\n" +
                "CHILD_ADDITION_FAILED\n" +
                "Bill Charlie Percy Ronald \n" +
                "Lucy \n" +
                "NONE\n" +
                "NONE\n" +
                "PERSON_NOT_FOUND\n" +
                "NONE\n" +
                "James Albus \n" +
                "NONE\n" +
                "Molly Lucy \n" +
                "Ron \n" +
                "NONE\n" +
                "NONE\n" +
                "NONE\n" +
                "NONE\n" +
                "CHILD_ADDED", systemOutRule.getLog()
                .trim());
    }

    private static class CrashAndBurn extends Bloodline {
    }


    private String getFilePath() throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
        Properties memberProperties = new Properties();
        memberProperties.load(input);
        return MessageFormat.format("{0}/{1}", new File("").getAbsolutePath(), memberProperties.getProperty("inputFileName"));
    }
}

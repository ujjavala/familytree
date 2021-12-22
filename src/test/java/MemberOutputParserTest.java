import com.family.tree.enums.Message;
import com.family.tree.io.MemberOutputParser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@Suite
@ExtendWith(MockitoExtension.class)
public class MemberOutputParserTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void shouldProcessOutput() {
        List<String> messages = new ArrayList<>(List.of("Linnie", "Jamie"));
        MemberOutputParser memberOutputParser = new MemberOutputParser();
        memberOutputParser.parse((List.of(Message.NONE, messages)));
        Assertions.assertEquals("NONE\n" + "Linnie Jamie", systemOutRule.getLog()
                .trim());    }
}

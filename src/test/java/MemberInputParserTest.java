import com.family.tree.io.MemberInputParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static com.family.tree.enums.Action.ADD_CHILD;
import static com.family.tree.enums.Action.GET_RELATIONSHIP;

@Suite
@ExtendWith(MockitoExtension.class)
public class MemberInputParserTest {

    String location = getFilePath();
    MemberInputParser parser = new MemberInputParser();

    public MemberInputParserTest() throws IOException {
    }

    @Test
    public void shouldReadInputFileAndReturnAList() throws IOException {
        Object output = parser.parse(location);
        Assertions.assertEquals(ArrayList.class, output.getClass());
    }

    @Test
    public void shouldReturnLengthAsFourIfActionIsAddChild() throws IOException {
        var list = parser.parse(location);
        list.stream().filter(action -> Objects.equals(action[0], ADD_CHILD.toString())).forEach(
                (String[] action) -> Assertions.assertEquals(4, action.length)
        );
    }

    @Test
    public void shouldReturnLengthAsThreeIfActionIsGetRelationship() throws IOException {
        var list = parser.parse(location);
        list.stream().filter(action -> Objects.equals(action[0], GET_RELATIONSHIP.toString())).forEach(
                (String[] action) -> Assertions.assertEquals(3, action.length)
        );
    }

    @Test
    public void shouldCheckIfFirstArgumentIsValidAction() throws IOException {
        var list = parser.parse(location);
        list.forEach(action -> Assertions.assertTrue(List.of(GET_RELATIONSHIP.toString(),
                ADD_CHILD.toString()).contains(action[0])));
    }


    private String getFilePath() throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
        Properties memberProperties = new Properties();
        memberProperties.load(input);
        return MessageFormat.format("{0}/{1}", new File("").getAbsolutePath(), memberProperties.getProperty("inputFileName"));
    }
}

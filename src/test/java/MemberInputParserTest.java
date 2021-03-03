import com.family.tree.io.MemberInputParser;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.family.tree.enums.Action.ADD_CHILD;
import static com.family.tree.enums.Action.GET_RELATIONSHIP;

@RunWith(MockitoJUnitRunner.class)
public class MemberInputParserTest {

    MemberInputParser parser = new MemberInputParser();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldReadInputFileAndReturnAList() throws IOException {
        Object output = parser.parse();
        Assert.assertEquals( ArrayList.class,output.getClass());
    }

    @Test
    public void shouldReturnLengthAsFourIfActionIsAddChild() throws IOException {
        var list = parser.parse();
        list.stream().filter(action -> Objects.equals(action[0], ADD_CHILD.toString())).forEach(
                (String[] action) -> Assert.assertEquals(4,action.length)
        );
    }

    @Test
    public void shouldReturnLengthAsThreeIfActionIsGetRelationship() throws IOException {
        var list = parser.parse();
        list.stream().filter(action -> Objects.equals(action[0], GET_RELATIONSHIP.toString())).forEach(
                (String[] action) -> Assert.assertEquals(3,action.length)
        );
    }

    @Test
    public void shouldCheckIfFirstArgumentIsValidAction() throws IOException {
        var list = parser.parse();
        list.forEach(action -> Assert.assertTrue(List.of(GET_RELATIONSHIP.toString(),
                ADD_CHILD.toString()).contains(action[0])));
    }
}

import com.family.tree.model.Member;
import com.family.tree.util.FamilyTreeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.junit.jupiter.MockitoExtension;

@Suite
@ExtendWith(MockitoExtension.class)
public class FamilyTreeUtilTest {

    @Test
    public void shouldGetRootAndGenerateTree() {
        Member output = FamilyTreeUtil.getRoot();
        Assertions.assertNotNull(output);
        Assertions.assertEquals("King Arthur", output.getName());
    }

    @Test
    public void shouldGenerateTreeWithCorrectSpouse() {
        Member output = FamilyTreeUtil.getRoot();
        Assertions.assertNotNull(output);
        Assertions.assertEquals(1, output.getSpouse().stream().count());
    }

    @Test
    public void shouldGenerateTreeWithCorrectChildren() {
        Member output = FamilyTreeUtil.getRoot();
        Assertions.assertNotNull(output);
        Assertions.assertEquals(5, output.getChildren().size());
    }

}

import com.family.tree.enums.Message;
import com.family.tree.service.impl.FamilyTreeServiceImpl;
import com.family.tree.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.Suite;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@Suite
@ExtendWith(MockitoExtension.class)
public class FamilyTreeUtilServiceTest {

    @Mock
    MemberServiceImpl service;
    List<String[]> list = new ArrayList<>();

    @BeforeEach
    public void setup()
    {
        String[]  action1 = {"ADD_CHILD", "Marcheline", "Anjelina", "Female"};
        String[]  action2 = {"ADD_CHILD", "Marcheline", "James", "Male"};
        String[]  action3 = {"ADD_CHILD", "Anjelina", "Shiloh", "Male"};
        String[]  action4 = {"ADD_CHILD", "Shiloh", "Vivienne", "Female"};
        String[]  action5 = {"GET_RELATIONSHIP", "Shiloh", "Siblings"};
        String[]  action6 = {"GET_RELATIONSHIP", "Vivienne", "Paternal-Uncle"};
        list.addAll(List.of(action1,action2,action3,action4,action5,action6));
    }

    @Test
    public void shouldReturnOutputWhenActionExecuted(){
        when(service.addChild(anyString(),anyString(),anyString())).thenReturn(Message.CHILD_ADDED);
        FamilyTreeServiceImpl familyTreeService = new FamilyTreeServiceImpl(list,service);
        var output = familyTreeService.executeAction();
        Assertions.assertNotNull(output);
        Assertions.assertEquals(6,output.size());
    }

    @Test
    public void shouldCheckLengthAsThreeIfActionIsGetRelationship() {
        when(service.addChild(anyString(),anyString(),anyString())).thenReturn(Message.CHILD_ADDED);
        String[]  invalid = {"GET_RELATIONSHIP", "Vivienne"};
        list.add(invalid);
        FamilyTreeServiceImpl familyTreeService = new FamilyTreeServiceImpl(list,service);
        var output = familyTreeService.executeAction();
        Assertions.assertTrue(output.contains(Message.INCORRECT_INPUT));
    }

    @Test
    public void shouldCheckLengthAsFourIfActionIsAddChild()  {
        when(service.addChild(anyString(),anyString(),anyString())).thenReturn(Message.CHILD_ADDED);
        String[]  invalid = {"ADD_CHILD", "Vivienne"};
        list.add(invalid);
        FamilyTreeServiceImpl familyTreeService = new FamilyTreeServiceImpl(list,service);
        var output = familyTreeService.executeAction();
        Assertions.assertTrue(output.contains(Message.INCORRECT_INPUT));
    }
}

import com.family.tree.enums.Message;
import com.family.tree.service.FamilyTreeService;
import com.family.tree.service.MemberService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FamilyTreeServiceTest {

    @Mock
    MemberService service;
    List<String[]> list = new ArrayList<>();

    @Before
    public void setup()
    {
        String[]  action1 = {"ADD_CHILD", "Marcheline", "Anjelina", "Female"};
        String[]  action2 = {"ADD_CHILD", "Marcheline", "James", "Male"};
        String[]  action3 = {"ADD_CHILD", "Anjelina", "Shiloh", "Male"};
        String[]  action4 = {"ADD_CHILD", "Shiloh", "Vivienne", "Female"};
        String[]  action5 = {"GET_RELATIONSHIP", "Shiloh", "Siblings"};
        String[]  action6 = {"GET_RELATIONSHIP", "Vivienne", "Paternal-Uncle"};
        list.add(action1);
        list.add(action2);
        list.add(action3);
        list.add(action4);
        list.add(action5);
        list.add(action6);
    }

    @Test
    public void shouldReturnOutputWhenActionExecuted(){
        when(service.addChild(anyString(),anyString(),anyString())).thenReturn(Message.CHILD_ADDED);
        FamilyTreeService familyTreeService = new FamilyTreeService(list,service);
        var output = familyTreeService.executeAction();
        Assert.assertNotNull(output);
        Assert.assertEquals(6,output.size());
    }

    @Test
    public void shouldCheckLengthAsThreeIfActionIsGetRelationship() {
        when(service.addChild(anyString(),anyString(),anyString())).thenReturn(Message.CHILD_ADDED);
        String[]  invalid = {"GET_RELATIONSHIP", "Vivienne"};
        list.add(invalid);
        FamilyTreeService familyTreeService = new FamilyTreeService(list,service);
        var output = familyTreeService.executeAction();
        Assert.assertTrue(output.contains(Message.INCORRECT_INPUT));
    }

    @Test
    public void shouldCheckLengthAsFourIfActionIsAddChild()  {
        when(service.addChild(anyString(),anyString(),anyString())).thenReturn(Message.CHILD_ADDED);
        String[]  invalid = {"ADD_CHILD", "Vivienne"};
        list.add(invalid);
        FamilyTreeService familyTreeService = new FamilyTreeService(list,service);
        var output = familyTreeService.executeAction();
        Assert.assertTrue(output.contains(Message.INCORRECT_INPUT));
    }
}

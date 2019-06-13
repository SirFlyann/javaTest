package luizalabs.Utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import luizalabs.Models.Item;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReflectionTest {
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void getFieldValueShouldReturnStringGivenValidFieldName() throws Exception {
    Item exampleItem =  new Item("1", "ean", "title", "brand", 10.0, 1);
    assertThat(Reflection.getFieldValue("ean", exampleItem)).getClass().equals(String.class);
  }
  
  @Test
  public void getFieldValueShouldThrowExceptionGivenInvalidFieldName() throws Exception {
    thrown.expect(NoSuchFieldException.class);
    thrown.expectMessage("O campo foo não existe ou não pode ser acessado!");
    
    Item exampleItem =  new Item("1", "ean", "title", "brand", 10.0, 1);
    Reflection.getFieldValue("foo", exampleItem);
  }
}

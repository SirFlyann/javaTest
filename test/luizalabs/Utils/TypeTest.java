package luizalabs.Utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TypeTest {
  
  @Test
  public void isStringShouldReturnTrueGivenStringWithLetters() {
    assertThat(Types.isString("String with letters")).isTrue();
    assertThat(Types.isString("String with letters 123")).isTrue();
  }

  @Test
  public void isStringShouldReturnFalseGivenStringWithoutLetters() {
    assertThat(Types.isString("12345")).isFalse();
    assertThat(Types.isString("123.45")).isFalse();
  }
}

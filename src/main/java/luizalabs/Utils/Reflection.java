package luizalabs.Utils;

import java.lang.reflect.Field;

public class Reflection {
  public static String getFieldValue(String stringField, Object object) throws NoSuchFieldException {
    Class<?> objectClass = object.getClass();
    Field field;
    try {
      field  = objectClass.getDeclaredField(stringField);
      field.setAccessible(true);
      Object fieldValue = field.get(object);
      return fieldValue.toString();
    } catch (Exception e) {
      throw new NoSuchFieldException("O campo " + stringField + " não existe ou não pode ser acessado!");
    }
  }
}

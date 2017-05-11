package myapp.util.veneer;

import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.beans.property.ObjectProperty;

import org.opendolphin.core.PresentationModel;

import myapp.util.AttributeDescription;

/**
 * @author Dieter Holz
 */
public class FX_EnumAttribute<T extends Enum<T>> extends FX_Attribute<ObjectProperty<T>, T> {

    private final Class<T> clazz;

    public FX_EnumAttribute(PresentationModel pm, AttributeDescription attributeDescription, Class<T> clazz) {
        super(pm, attributeDescription,
              createRegex(clazz),
              new EnumAttributeAdapter<>(valueAttribute(pm, attributeDescription), clazz));
        this.clazz = clazz;
    }

    @Override
    protected String format(T value) {
        return value == null ? "" : value.name();
    }

    @Override
    protected T convertToValue(String string) {
        return Enum.valueOf(clazz, string);
    }


    public void setValue(T value){
        valueProperty().setValue(value);
    }

    public T getValue(){
        return valueProperty().getValue();
    }


    static <E extends Enum<E>> String createRegex(Class<E> enumClass){
        return Arrays.stream(enumClass.getEnumConstants())
              .map(e -> "((?i)" + e.name() + "){1}")
              .collect(Collectors.joining("|"));
    }
}
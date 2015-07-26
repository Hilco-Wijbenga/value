package org.cavebeetle.valueclass.internal;

import static org.cavebeetle.valueclass.internal.impl.MissingValueField.newMissingValueField;
import java.util.Map;
import org.cavebeetle.valueclass.internal.impl.BasicEntity;
import org.cavebeetle.valueclass.internal.impl.MissingValueField;
import com.google.common.collect.ImmutableMap;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

public final class DefaultValues
{
    private static final Map<TypeName, String> DEFAULT_VALUES;

    static
    {
        DEFAULT_VALUES = ImmutableMap.<TypeName, String> builder()
                .put(TypeName.BOOLEAN, "false")
                .put(TypeName.BYTE, "0")
                .put(TypeName.CHAR, "0")
                .put(TypeName.DOUBLE, "0.0d")
                .put(TypeName.FLOAT, "0.0f")
                .put(TypeName.INT, "0")
                .put(TypeName.LONG, "0L")
                .put(TypeName.SHORT, "0")
                .build();
    }

    public static final String primitiveDefaultValue(final TypeName type)
    {
        return DEFAULT_VALUES.get(type);
    }

    public static final String stringDefaultValue(final TypeName type)
    {
        return "\"\"";
    }

    public static final String entityDefaultValue(final BasicEntity basicEntity)
    {
        final String missingValueFieldName = basicEntity.valueModel().missingValueFieldName();
        final ClassName type = basicEntity.type();
        final MissingValueField missingValueField = newMissingValueField(missingValueFieldName, type);
        final String name = type.simpleName();
        return String.format("%s.%s", name, missingValueField.name());
    }
}

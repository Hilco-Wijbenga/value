package org.cavebeetle.valueclass.internal.impl;

import static java.lang.String.format;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static org.cavebeetle.valueclass.internal.impl.Misc.makeLeadingUpperCase;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;

public final class MissingValueField
{
    public static final MissingValueField newMissingValueField(final String mask, final ClassName type)
    {
        final String name = format(mask, makeLeadingUpperCase(type.simpleName()));
        return new MissingValueField(type, name);
    }

    private final ClassName type;
    private final String name;

    private MissingValueField(final ClassName type, final String name)
    {
        this.type = type;
        this.name = name;
    }

    public String name()
    {
        return name;
    }

    public FieldSpec toFieldSpec()
    {
        final FieldSpec fieldSpec = FieldSpec
                .builder(type, name)
                .addModifiers(PUBLIC, STATIC, FINAL)
                .build();
        return fieldSpec;
    }
}

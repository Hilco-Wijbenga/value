package org.cavebeetle.valueclass.internal.impl;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;
import com.google.common.collect.Interner;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;

public final class InternerField
{
    public static final InternerField newInternerField(final ClassName type)
    {
        final ParameterizedTypeName internerType = ParameterizedTypeName.get(ClassName.get(Interner.class), type);
        return new InternerField(internerType);
    }

    private final ParameterizedTypeName internerType;

    private InternerField(final ParameterizedTypeName internerType)
    {
        this.internerType = internerType;
    }

    public FieldSpec toFieldSpec()
    {
        final FieldSpec interner = FieldSpec
                .builder(internerType, "INTERNER")
                .addModifiers(PRIVATE, STATIC, FINAL)
                .build();
        return interner;
    }
}

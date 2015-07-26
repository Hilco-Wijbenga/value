package org.cavebeetle.valueclass.internal.impl;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import javax.lang.model.element.Name;
import org.cavebeetle.valueclass.ValueModel;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

public final class Component
{
    public static final Component newComponent(final BasicComponent basicComponent)
    {
        return new Component(basicComponent);
    }

    private final boolean primitive;
    private final boolean string;
    private final String defaultValue;
    private final Name getterName;
    private final Name setterName;
    private final FieldSpec field;
    private final FieldSpec publicFinalField;
    private final FieldSpec privateFinalField;
    private final FieldSpec privateField;
    private final ParameterSpec finalParameter;
    private final MethodSpec getter;

    private Component(final BasicComponent basicComponent)
    {
        primitive = basicComponent.type().primitive();
        string = basicComponent.type().string();
        defaultValue = basicComponent.type().defaultValue();
        final TypeName type = basicComponent.type().typeName();
        final String name = basicComponent.name().toString();
        final String upperCasedName = "" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        switch (basicComponent.parent().valueModel().accessorType())
        {
            case USE_PUBLIC_FINAL_FIELDS:
                getterName = StringName.SENTINEL;
                break;
            case USE_JAVA_BEAN_GETTERS:
                if (basicComponent.type().primitive() && basicComponent.type().typeName() == TypeName.BOOLEAN)
                {
                    getterName = StringName.newName("is" + upperCasedName);
                }
                else
                {
                    getterName = StringName.newName("get" + upperCasedName);
                }
                break;
            default:
                getterName = StringName.newName(name);
        }
        setterName = StringName.newName("set" + upperCasedName);
        field = FieldSpec.builder(type, name).build();
        publicFinalField = FieldSpec.builder(type, name).addModifiers(PUBLIC, FINAL).build();
        privateFinalField = FieldSpec.builder(type, name).addModifiers(PRIVATE, FINAL).build();
        privateField = FieldSpec.builder(type, name).addModifiers(PRIVATE).build();
        finalParameter = ParameterSpec.builder(type, name).addModifiers(FINAL).build();
        if (basicComponent.parent().valueModel().accessorType() == ValueModel.AccessorType.USE_PUBLIC_FINAL_FIELDS)
        {
            getter = null;
        }
        else
        {
            getter = MethodSpec.methodBuilder(getterName.toString())
                    .addModifiers(PUBLIC)
                    .returns(type)
                    .addStatement("return $N", name)
                    .build();
        }
    }

    public boolean primitive()
    {
        return primitive;
    }

    public boolean string()
    {
        return string;
    }

    public String defaultValue()
    {
        return defaultValue;
    }

    public Name getterName()
    {
        return getterName;
    }

    public Name setterName()
    {
        return setterName;
    }

    public FieldSpec asField()
    {
        return field;
    }

    public FieldSpec asPublicFinalField()
    {
        return publicFinalField;
    }

    public FieldSpec asPrivateFinalField()
    {
        return privateFinalField;
    }

    public FieldSpec asPrivateField()
    {
        return privateField;
    }

    public ParameterSpec asFinalParameter()
    {
        return finalParameter;
    }

    public MethodSpec getter()
    {
        return getter;
    }
}

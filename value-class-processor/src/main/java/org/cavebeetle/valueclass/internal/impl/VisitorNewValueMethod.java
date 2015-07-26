package org.cavebeetle.valueclass.internal.impl;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;
import org.cavebeetle.valueclass.internal.ClassBuilderVisitor;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

public final class VisitorNewValueMethod
        implements
            ClassBuilderVisitor
{
    @Override
    public void visit(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addMethod(makeNewValueMethod(entity));
    }

    public MethodSpec makeNewValueMethod(final Entity entity)
    {
        final ClassName valueType = entity.type();
        final MethodSpec newValue = MethodSpec.methodBuilder("new" + entity.name())
                .addModifiers(PRIVATE, STATIC, FINAL)
                .returns(valueType)
                .addParameter(ParameterSpec.builder(entity.builderType(), "builder", FINAL).build())
                .addCode(makeNewValueCodeBlock(valueType))
                .build();
        return newValue;
    }

    public static final CodeBlock makeNewValueCodeBlock(final ClassName valueType)
    {
        return CodeBlock.builder()
                .addStatement("return new $T(builder)", valueType)
                .build();
    }
}

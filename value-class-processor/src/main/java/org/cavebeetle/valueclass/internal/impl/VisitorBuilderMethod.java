package org.cavebeetle.valueclass.internal.impl;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import org.cavebeetle.valueclass.internal.ClassBuilderVisitor;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec.Builder;

public final class VisitorBuilderMethod
        implements
            ClassBuilderVisitor
{
    @Override
    public void visit(final Builder builder, final Entity entity)
    {
        builder.addMethod(MethodSpec.methodBuilder("builder")
                .addModifiers(PUBLIC, STATIC, FINAL)
                .returns(entity.builderType())
                .addStatement("return new $T()", entity.builderType())
                .build());
    }
}

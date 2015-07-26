package org.cavebeetle.valueclass.internal.impl;

import static javax.lang.model.element.Modifier.PUBLIC;
import org.cavebeetle.valueclass.internal.ClassBuilderVisitor;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public final class VisitorCopyMethod
        implements
            ClassBuilderVisitor
{
    @Override
    public void visit(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addMethod(MethodSpec.methodBuilder("copy")
                .addModifiers(PUBLIC)
                .returns(entity.builderType())
                .addStatement("return new $T(this)", entity.builderType())
                .build());
    }
}

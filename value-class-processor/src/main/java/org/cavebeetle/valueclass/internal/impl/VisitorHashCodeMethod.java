package org.cavebeetle.valueclass.internal.impl;

import static javax.lang.model.element.Modifier.PUBLIC;
import org.cavebeetle.valueclass.internal.ClassBuilderVisitor;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public final class VisitorHashCodeMethod
        implements
            ClassBuilderVisitor
{
    @Override
    public void visit(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addMethod(makeHashCodeMethod(entity));
    }

    public static final MethodSpec makeHashCodeMethod(final Entity entity)
    {
        return MethodSpec.methodBuilder("hashCode")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .returns(int.class)
                .addCode(makeHashCodeCodeBlock(entity))
                .build();
    }

    private static final String PRIMITIVE_PATTERN = "result = prime * result + $N";
    private static final String BOOLEAN_PATTERN = "result = prime * result + ($N ? 1231 : 1237)";
    private static final String INSTANCE_PATTERN = "result = prime * result + $N.hashCode()";

    public static final CodeBlock makeHashCodeCodeBlock(final Entity entity)
    {
        final CodeBlock.Builder codeBlockBuilder = CodeBlock.builder()
                .addStatement("final int prime = $L", entity.prime())
                .addStatement("int result = 1");
        for (final FieldSpec field : entity.components().asFields())
        {
            final String pattern = field.type.isPrimitive()
                    ? field.type == TypeName.BOOLEAN
                            ? BOOLEAN_PATTERN
                            : PRIMITIVE_PATTERN
                    : INSTANCE_PATTERN;
            codeBlockBuilder.addStatement(pattern, field.name);
        }
        codeBlockBuilder.addStatement("return result");
        return codeBlockBuilder.build();
    }
}

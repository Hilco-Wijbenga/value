package org.cavebeetle.valueclass.internal.impl;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static org.cavebeetle.valueclass.internal.impl.Misc.PRECONDITIONS;
import org.cavebeetle.valueclass.internal.ClassBuilderVisitor;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

public final class VisitorBuilderClass
        implements
            ClassBuilderVisitor
{
    @Override
    public void visit(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addType(makeBuilderClass(entity));
    }

    public static final TypeSpec makeBuilderClass(final Entity entity)
    {
        final TypeSpec.Builder builder = TypeSpec.classBuilder("Builder").addModifiers(PUBLIC, STATIC, FINAL);
        addFieldPrivateFinalOriginal(builder, entity);
        addComponentsAsPrivateFields(builder, entity);
        addDefaultConstructor(builder, entity);
        addConstructor(builder, entity);
        addSetters(builder, entity);
        addBuildMethod(builder, entity);
        return builder.build();
    }

    public static final void addFieldPrivateFinalOriginal(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addField(FieldSpec.builder(entity.type(), "original", PRIVATE, FINAL).build());
    }

    public static final void addComponentsAsPrivateFields(final TypeSpec.Builder builder, final Entity entity)
    {
        for (final FieldSpec field : entity.components().asPrivateFields())
        {
            builder.addField(field);
        }
    }

    public static final void addDefaultConstructor(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(PUBLIC)
                .addStatement("this($N)", entity.missingValueField().name())
                .build());
    }

    public static final void addConstructor(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(PUBLIC)
                .addParameter(entity.type(), "original", FINAL)
                .addCode(makeConstructorCodeBlock(entity))
                .build());
    }

    public static final CodeBlock makeConstructorCodeBlock(final Entity entity)
    {
        final CodeBlock.Builder codeBlockBuilder = CodeBlock.builder()
                .addStatement("$T.checkNotNull(original, \"Missing 'original'.\")", PRECONDITIONS)
                .addStatement("this.original = original");
        for (final FieldSpec field : entity.components().asFields())
        {
            codeBlockBuilder.addStatement("this.$N = original.$N", field.name, field.name);
        }
        return codeBlockBuilder.build();
    }

    public static final void addSetters(final TypeSpec.Builder builder, final Entity entity)
    {
        for (final Component component : entity.components())
        {
            final ParameterSpec parameter = component.asFinalParameter();
            builder.addMethod(MethodSpec.methodBuilder(component.setterName().toString())
                    .addModifiers(PUBLIC)
                    .returns(entity.builderType())
                    .addParameter(parameter)
                    .addCode(makeSetterCodeBlock(parameter))
                    .build());
        }
    }

    private static final String PRIMITIVE_SETTER_CODE = "this.$N = $N == original.$N ? original.$N : $N";
    private static final String INSTANCE_SETTER_CODE = "this.$N = $N.equals(original.$N) ? original.$N : $N";

    public static final CodeBlock makeSetterCodeBlock(final ParameterSpec parameter)
    {
        final String paramName = parameter.name;
        final String setterCode = parameter.type.isPrimitive() ? PRIMITIVE_SETTER_CODE : INSTANCE_SETTER_CODE;
        return CodeBlock.builder()
                .addStatement("$T.checkNotNull($N, \"Missing '$N'.\")", PRECONDITIONS, paramName, paramName)
                .addStatement(setterCode, paramName, paramName, paramName, paramName, paramName)
                .addStatement("return this")
                .build();
    }

    public static final void addBuildMethod(final TypeSpec.Builder builder, final Entity entity)
    {
        builder.addMethod(MethodSpec.methodBuilder("build")
                .addModifiers(PUBLIC)
                .returns(entity.type())
                .addStatement("return new$N(this)", entity.name())
                .build());
    }
}

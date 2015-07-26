package org.cavebeetle.valueclass.internal.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import java.io.IOException;
import java.util.Collection;
import javax.annotation.processing.Filer;
import javax.inject.Singleton;
import org.cavebeetle.valueclass.internal.ClassBuilderVisitor;
import org.cavebeetle.valueclass.internal.EntityWriter;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public final class DefaultEntityWriter
        implements
            EntityWriter
{
    @Singleton
    public static final class DefaultBuilder
            implements
                Builder
    {
        @Override
        public EntityWriter make(final Filer filer)
        {
            return new DefaultEntityWriter(filer);
        }
    }

    private static final Collection<ClassBuilderVisitor> VISITORS;

    static
    {
        VISITORS = newArrayList(
                new VisitorImplementComparable(),
                new VisitorMissingValueField(),
                new VisitorConstructorMethods(),
                new VisitorAccessors(),
                new VisitorNewValueMethod(),
                new VisitorHashCodeMethod(),
                new VisitorEqualsMethod(),
                new VisitorToStringMethod(),
                new VisitorCopyMethod(),
                new VisitorBuilderMethod(),
                new VisitorBuilderClass());
    }

    public static final JavaFile makeJavaFile(final Entity entity)
    {
        return JavaFile.builder(entity.type().packageName(), makeValueClass(entity))
                .indent("    ")
                .skipJavaLangImports(true)
                .build();
    }

    public static final TypeSpec makeValueClass(final Entity entity)
    {
        final String valueClassName = entity.name().toString();
        final TypeSpec.Builder valueClassSpecBuilder = classBuilder(valueClassName).addModifiers(PUBLIC, FINAL);
        for (final ClassBuilderVisitor visitor : VISITORS)
        {
            visitor.visit(valueClassSpecBuilder, entity);
        }
        return valueClassSpecBuilder.build();
    }

    public static final void writeJavaFile(final Filer filer, final JavaFile javaFile)
    {
        try
        {
            javaFile.writeTo(filer);
        }
        catch (final IOException e)
        {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private final Filer filer;

    public DefaultEntityWriter(final Filer filer)
    {
        checkNotNull(filer, "Missing 'filer'.");
        this.filer = filer;
    }

    @Override
    public void create(final Entity entity)
    {
        checkNotNull(entity, "Missing 'entity'.");
        writeJavaFile(filer, makeJavaFile(entity));
    }
}

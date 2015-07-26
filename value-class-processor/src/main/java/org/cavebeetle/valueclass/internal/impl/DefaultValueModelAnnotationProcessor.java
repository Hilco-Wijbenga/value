package org.cavebeetle.valueclass.internal.impl;

import static java.lang.String.format;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;
import org.cavebeetle.valueclass.ValueModel;
import org.cavebeetle.valueclass.internal.EntityWriter;
import org.cavebeetle.valueclass.internal.ValueModelAnnotationProcessor;

@Singleton
public final class DefaultValueModelAnnotationProcessor
        extends
            AbstractProcessor
        implements
            ValueModelAnnotationProcessor
{
    private final EntityWriter.Builder entityWriterBuilder;
    private Messager messager;
    private Filer filer;
    private Elements elementUtils;

    @Inject
    public DefaultValueModelAnnotationProcessor(final EntityWriter.Builder entityWriterBuilder)
    {
        this.entityWriterBuilder = entityWriterBuilder;
    }

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnvironment)
    {
        elementUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnvironment)
    {
        final Entities entities = extractEntities(roundEnvironment);
        final EntityWriter entityWriter = entityWriterBuilder.make(filer);
        for (final Entity entity : entities)
        {
            entityWriter.create(entity);
            printLine(format("Created '%s'.", entity.fullyQualifiedName()));
        }
        return true;
    }

    public Entities extractEntities(final RoundEnvironment roundEnvironment)
    {
        final Set<? extends Element> entityElements = roundEnvironment.getElementsAnnotatedWith(ValueModel.class);
        if (entityElements.isEmpty())
        {
            return Entities.EMPTY;
        }
        else
        {
            return new Entities(elementUtils, entityElements);
        }
    }

    public void printLine(final String line)
    {
        messager.printMessage(Kind.NOTE, line);
    }
}

package org.cavebeetle.valueclass.impl;

import static javax.lang.model.SourceVersion.RELEASE_8;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.TypeElement;
import org.cavebeetle.valueclass.internal.DaggerAnnotationProcessor;
import org.cavebeetle.valueclass.internal.ValueModelAnnotationProcessor;
import com.google.auto.service.AutoService;

@AutoService(Processor.class)
@SupportedAnnotationTypes("org.cavebeetle.valueclass.ValueModel")
@SupportedSourceVersion(RELEASE_8)
public final class AnnotationProcessor
        extends
            AbstractProcessor
{
    private final ValueModelAnnotationProcessor annotationProcessor;

    public AnnotationProcessor()
    {
        annotationProcessor = DaggerAnnotationProcessor.create().valueModelAnnotationProcessor();
    }

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnvironment)
    {
        annotationProcessor.init(processingEnvironment);
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnvironment)
    {
        return annotationProcessor.process(annotations, roundEnvironment);
    }
}

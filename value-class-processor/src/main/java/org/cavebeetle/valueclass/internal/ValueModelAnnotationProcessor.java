package org.cavebeetle.valueclass.internal;

import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

public interface ValueModelAnnotationProcessor
{
    void init(ProcessingEnvironment processingEnvironment);

    boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment);
}

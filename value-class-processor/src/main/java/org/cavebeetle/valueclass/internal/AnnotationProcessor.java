package org.cavebeetle.valueclass.internal;

import javax.inject.Singleton;
import org.cavebeetle.valueclass.internal.impl.ValueModelAnnotationProcessorModule;
import dagger.Component;

@Component(modules = ValueModelAnnotationProcessorModule.class)
@Singleton
public interface AnnotationProcessor
{
    ValueModelAnnotationProcessor valueModelAnnotationProcessor();

    PrimeGenerator primeGenerator();
}

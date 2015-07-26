package org.cavebeetle.valueclass.internal.impl;

import javax.inject.Inject;
import org.cavebeetle.valueclass.internal.EntityWriter;
import org.cavebeetle.valueclass.internal.IntGenerator;
import org.cavebeetle.valueclass.internal.PrimeGenerator;
import org.cavebeetle.valueclass.internal.ValueModelAnnotationProcessor;
import dagger.Module;
import dagger.Provides;

@Module
public final class ValueModelAnnotationProcessorModule
{
    @Provides
    @Inject
    public ValueModelAnnotationProcessor provideValueModelAnnotationProcessor(final EntityWriter.Builder entityWriterBuilder)
    {
        return new DefaultValueModelAnnotationProcessor(entityWriterBuilder);
    }

    @Provides
    public EntityWriter.Builder provideEntityWriterBuilder()
    {
        return new DefaultEntityWriter.DefaultBuilder();
    }

    @Provides
    public IntGenerator.Builder provideIntGeneratorBuilder()
    {
        return new DefaultIntGenerator.DefaultBuilder();
    }

    @Provides
    @Inject
    public PrimeGenerator providePrimeGenerator(final IntGenerator.Builder intGeneratorBuilder)
    {
        return new DefaultPrimeGenerator(intGeneratorBuilder);
    }
}

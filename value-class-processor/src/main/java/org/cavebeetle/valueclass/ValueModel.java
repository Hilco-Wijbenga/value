package org.cavebeetle.valueclass;

public @interface ValueModel
{
    /**
     * Indicates how access to fields should be implemented.
     */
    public enum AccessorType
    {
        /**
         * <p>
         * Use "field methods". This is the default.
         * </p>
         *
         * <pre>
         * private final myType myField;
         *
         * public myType myField()
         * {
         *     return myField;
         * }
         * </pre>
         */
        DEFAULT_USE_FIELD_METHODS,
        /**
         * <p>
         * Use regular JavaBean getters.
         * </p>
         *
         * <pre>
         * private final myType myField;
         * private final boolean myBooleanField;
         *
         * public myType getMyField()
         * {
         *     return myField;
         * }
         *
         * public boolean isMyBooleanField()
         * {
         *     return myBooleanField;
         * }
         * </pre>
         * <p>
         * (A getter returning a {@code boolean} will use {@code is} instead of {@code get}.)
         * </p>
         */
        USE_JAVA_BEAN_GETTERS,
        /**
         * <p>
         * Make all fields directly accessible.
         * </p>
         *
         * <pre>
         * public final myType myField;
         * </pre>
         */
        USE_PUBLIC_FINAL_FIELDS
    }

    public enum InternerUse
    {
        /**
         * <p>
         * Do not use an {@code Interner}.
         * </p>
         * <p>
         * Without an {@code Interner} you can have multiple, different instances that are all considered equal.
         * </p>
         * <p>
         * In order to determine whether two instances are the same, you <em>MUST</em> use {@code equals}.
         * </p>
         */
        DEFAULT_USE_NO_INTERNER,
        /**
         * <p>
         * Use an {@code Interner}.
         * </p>
         * <p>
         * By using an {@code Interner} you are guaranteed to never have multiple, equal instances. I.e, instances
         * really can be treated as values.
         * </p>
         * <p>
         * In order to determine whether two instances are equal, you can simply use {@code ==}. Obviously,
         * {@code equals} works as well.
         * </p>
         */
        USE_INTERNER
    }

    /**
     * <p>
     * Identifies the name of the {@code null} value. This defaults to {@code SENTINEL}.
     * </p>
     * A single {@code %s} will be replaced by the name of the value class. E.g. {@code NULL_%s} generates
     * {@code NULL_VALUE_CLASS}.
     *
     * @return the name of the {@code null} value.
     */
    String missingValueFieldName() default "SENTINEL";

    /**
     * Indicates which type of accessor should be implemented.
     *
     * @return the type of accessor to implement.
     */
    AccessorType accessorType() default AccessorType.DEFAULT_USE_FIELD_METHODS;

    /**
     * Indicates whether to use an {@code Interner}.
     *
     * @return whether to use an {@code Interner}.
     */
    InternerUse internerUse() default InternerUse.DEFAULT_USE_NO_INTERNER;
}

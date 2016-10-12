package javax.validation;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by hendrikebbers on 12.10.16.
 */
public interface BeanValidator<T> {

    <V> BeanValidator<T> withProperty(String propertyName, Supplier<V> valueSupplier);

    <V> BeanValidator<T> withProperty(String propertyName, Supplier<V> valueSupplier, Consumer<Set<ConstraintViolation<V>>> propertyViolationConsumer);

    Set<ConstraintViolation<T>> validate(Class<?>... groups);
}

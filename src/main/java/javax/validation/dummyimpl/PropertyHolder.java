package javax.validation.dummyimpl;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PropertyHolder<B, P> {

    private final Supplier<P> valueSupplier;

    private final Consumer<Set<ConstraintViolation<B>>> propertyViolationConsumer;

    public PropertyHolder(Supplier<P> valueSupplier, Consumer<Set<ConstraintViolation<B>>> propertyViolationConsumer) {
        this.valueSupplier = valueSupplier;
        this.propertyViolationConsumer = propertyViolationConsumer;
    }

    public Supplier<P> getValueSupplier() {
        return valueSupplier;
    }

    public Consumer<Set<ConstraintViolation<B>>> getPropertyViolationConsumer() {
        return propertyViolationConsumer;
    }
}

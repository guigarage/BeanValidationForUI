package javax.validation.dummyimpl;

import javax.validation.BeanValidator;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by hendrikebbers on 12.10.16.
 */
public class DummyBeanValidator<T> implements BeanValidator {

    private final Map<String, PropertyHolder> properties = new HashMap<>();

    private final Class<T> beanClass;

    private final Validator validator;

    public DummyBeanValidator(Validator validator, Class<T> beanClass) {
        this.validator = validator;
        this.beanClass = beanClass;
    }

    @Override
    public BeanValidator withProperty(final String propertyName, final Supplier valueSupplier) {
        return withProperty(propertyName, valueSupplier, v -> {});
    }

    @Override
    public BeanValidator withProperty(final String propertyName, final Supplier valueSupplier, final Consumer propertyViolationConsumer) {
        properties.put(propertyName, new PropertyHolder(valueSupplier, propertyViolationConsumer));
        return this;
    }

    private Set<ConstraintViolation<T>> validateProperty(String property, Class[] groups) {
        try {
            T model = beanClass.newInstance();
            PropertyHolder propertyHolder = properties.get(property);

            setPrivileged(property, model, propertyHolder.getValueSupplier().get());

            Set<ConstraintViolation<T>> violationsForKey = validator.validateProperty(model, property, groups);
            propertyHolder.getPropertyViolationConsumer().accept(violationsForKey);
            return violationsForKey;
        } catch (Exception e) {
            throw new RuntimeException("ERROR", e);
        }
    }

    @Override
    public Set<ConstraintViolation> validate(Class[] groups) {
        try {
            Set<ConstraintViolation> result = new HashSet<>();
            properties.keySet().forEach(p -> result.addAll(validateProperty(p, groups)));
            return result;
        } catch (Exception e) {
            throw new RuntimeException("ERROR", e);
        }
    }

    private Field getFieldForProperty(final Class<?> type, final String property) {
        Class<?> i = type;
        while (i != null && i != Object.class) {
            for (Field field : Arrays.asList(i.getDeclaredFields())) {
                if (field.getName().equals(property)) {
                    return field;
                }
            }
        }
        return null;
    }

    private void setPrivileged(final String property, final Object bean,
                                     final Object value) {
        final Field field = getFieldForProperty(bean.getClass(), property);
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                boolean wasAccessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    field.set(bean, value);
                    return null; // return nothing...
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    throw new IllegalStateException("Cannot set field: "
                            + field, ex);
                } finally {
                    field.setAccessible(wasAccessible);
                }
            }
        });
    }
}

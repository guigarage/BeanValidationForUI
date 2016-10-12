package javax.validation;

import javax.validation.dummyimpl.DummyBeanValidator;

/**
 * Created by hendrikebbers on 12.10.16.
 */
public class BeanValidatorProvider {

    public static <T> BeanValidator<T> build(final Class<T> beanClass) {
        final Configuration<?> validationConf = Validation.byDefaultProvider().configure();
        final Validator validator = validationConf.buildValidatorFactory().getValidator();
        return new DummyBeanValidator<>(validator, beanClass);
    }
}

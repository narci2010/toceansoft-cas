/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate.core;

import com.toceansoft.cas.support.validate.*;
import com.toceansoft.cas.support.validate.exception.ValidateSenderException;

/**
 * 默认的校验服务
 *
 * @author Narci.Lee
 * @date 2018/11/2
 * @since 2.3.8
 */
public class DefaultValidateService<K extends Informative, T extends Credential, O extends ValidateCredential> implements IValidateService<T, O> {
    private InformativeGenerator<K, T> generator;
    private IStore<K, T> store;
    private ISender<K> sender;
    private IValidator<O> validator;

    public DefaultValidateService(InformativeGenerator<K, T> generator, IStore<K, T> store, ISender<K> sender, IValidator<O> validator) {
        this.generator = generator;
        this.store = store;
        this.sender = sender;
        this.validator = validator;
    }

    public InformativeGenerator<K, T> getGenerator() {
        return generator;
    }

    public IStore<K, T> getStore() {
        return store;
    }

    public ISender<K> getSender() {
        return sender;
    }

    public IValidator<O> getValidator() {
        return validator;
    }

    @Override
    public void send(T t) throws ValidateSenderException {
        K informative = getGenerator().generate(t);
        getSender().send(informative);
        getStore().save(informative);
    }

    @Override
    public ValidateResult validate(O o) {
        return getValidator().identify(o);
    }
}

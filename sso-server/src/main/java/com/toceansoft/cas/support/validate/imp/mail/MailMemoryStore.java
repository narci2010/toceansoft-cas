/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */

package com.toceansoft.cas.support.validate.imp.mail;

import java.util.HashMap;
import java.util.Map;

import com.toceansoft.cas.support.validate.IStore;

/**
 * @author Narci.Lee
 * @date 2017/11/2
 * @since 2.3.8
 */
public class MailMemoryStore implements IStore<MailInformative, MailCredential> {
    private Map<String, MailInformative> store = new HashMap<>();

    @Override
    public void save(MailInformative mailInformative) {
        store.put(mailInformative.id(), mailInformative);
    }

    @Override
    public MailInformative get(MailCredential mailCredential) {
        return store.get(mailCredential.id());
    }

    @Override
    public void remove(MailCredential mailCredential) {
        store.remove(mailCredential.id());
    }
}

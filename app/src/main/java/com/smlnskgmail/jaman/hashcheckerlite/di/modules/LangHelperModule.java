package com.smlnskgmail.jaman.hashcheckerlite.di.modules;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.logic.locale.api.LangHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class LangHelperModule {

    private final LangHelper langHelper;

    public LangHelperModule(
            @NonNull LangHelper langHelper
    ) {
        this.langHelper = langHelper;
    }

    @NonNull
    @Provides
    public LangHelper langHelper() {
        return langHelper;
    }

}

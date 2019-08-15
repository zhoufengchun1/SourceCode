package com.kangYh.fangdou2.di.modules.di.components;


import com.kangYh.fangdou2.app.home.HomeFragment;
import com.kangYh.fangdou2.app.home.activity.MainActivity;
import com.kangYh.fangdou2.di.modules.ActivityModule;
import com.kangYh.fangdou2.di.modules.di.scopes.Scopes;

import dagger.Component;

/**
 * Created by solo on 2018/1/10.
 */

@Scopes.Activity
@Component(modules = {ActivityModule.class}, dependencies = {ApplicationComponent.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(HomeFragment fragment);

}

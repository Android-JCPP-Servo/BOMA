package com.example.boma;

import java.lang.ref.WeakReference;

public class Application extends android.app.Application {
    MVPPresenter presenter;
    WeakReference<MainActivity> mainActivity;

    public MVPPresenter getPresenter(){
        return this.presenter;
    }

    public void setMainActivity(MainActivity activity){

        presenter = new MVPPresenter(activity);
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }
}

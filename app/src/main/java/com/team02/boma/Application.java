package com.team02.boma;

/**
 * Application
 *  This class extends android.app.Application to provide something similar
 *  to global variable access
 */
public class Application extends android.app.Application {
    private MVPPresenter presenter;

    /**
     * getPresenter
     *  This method returns the presenter object
     * @return returns the presenter object
     */
    public MVPPresenter getPresenter(){
        return this.presenter;
    }

    /**
     * setMainActivity
     * This method is used to initialize the MVPPresenter when the MainActivity is known
     * @param activity // MainActivity for data access.
     */
    public void setMainActivity(MainActivity activity){
        presenter = new MVPPresenter(activity);
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }
}

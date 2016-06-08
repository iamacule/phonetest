package phamhungan.com.phonetestv3.ui;

/**
 * Created by MrAn on 03-Jun-16.
 */
public interface InitializeView {

    /**
     * This function are initialize child view
     * Ex : Set text , init list adapter ..vv.v..
     */
    void initializeChildView();

    /**
     * This function are initialize child value
     * Ex : Value of child needed
     */
    void initializeChildValue();

    /**
     * This function are initialize child action
     * Init receiver , service ..vv.v..
     */
    void initializeChildAction();
}
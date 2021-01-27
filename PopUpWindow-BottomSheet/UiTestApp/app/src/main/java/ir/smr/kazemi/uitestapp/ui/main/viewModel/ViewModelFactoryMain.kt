package ir.smr.kazemi.uitestapp.ui.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.smr.kazemi.uitestapp.ui.main.MainActivity

/**
 * By the grace of Allah, Created by Sayed-MohammadReza Kazemi
 * on 9/29/20.
 */
class ViewModelFactoryMain(private val activity: MainActivity) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return ViewModelMain(activity) as T
    }
}
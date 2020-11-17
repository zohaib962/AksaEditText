package com.example.customaksaedittext

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class DummyModel : BaseObservable() {



    var password: String? = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }

}
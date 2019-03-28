package com.burlaka.utils.resource

import android.content.Context
import com.bsx.askan.utils.resource.ResourceManager


class AndroidResourceManager
constructor(private val context: Context) : ResourceManager {

    override fun getString(resourceId: Int): String {
        return context.resources.getString(resourceId)
    }

    override fun getInteger(resourceId: Int): Int {
        return context.resources.getInteger(resourceId)
    }

}
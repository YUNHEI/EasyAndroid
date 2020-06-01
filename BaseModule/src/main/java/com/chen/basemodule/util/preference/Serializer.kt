package com.chen.basemodule.util.preference

import java.lang.reflect.Type

interface Serializer {

    fun serialize(toSerialize: Any?): String?

    fun deserialize(serialized: String?, type: Type): Any?
}

package net.legio.eve.engine.data

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class NumericBooleanDeserializer: JsonDeserializer<Boolean>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Boolean {
        return "1" == p?.text
    }
}
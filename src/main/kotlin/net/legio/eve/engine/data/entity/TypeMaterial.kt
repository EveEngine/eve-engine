package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class TypeMaterial (
    @Id override val objectId: Long, 
    val typeID: Int,
    val materialTypeID: Int,
    val quantity: Int
): ESDData
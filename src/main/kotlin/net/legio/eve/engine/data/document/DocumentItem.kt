package net.legio.eve.engine.data.document

import org.dizitart.no2.NitriteId


internal interface DocumentItem {
    val objectId: NitriteId?
}
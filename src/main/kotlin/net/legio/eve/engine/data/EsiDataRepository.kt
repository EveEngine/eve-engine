package net.legio.eve.engine.data

import net.legio.eve.engine.core.IWorkspace
import net.legio.eve.engine.toAbsoluteString
import java.nio.file.Paths

abstract class EsiDataRepository(private val workspace: IWorkspace): IEsiDataRepository {

    protected val dbPath = Paths.get(workspace.dataDirectory().toAbsoluteString(), "esi.db")

}
package net.legio.eve.engine.core.auth

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.legio.eve.engine.Result
import net.legio.eve.engine.core.IWorkspace
import net.legio.eve.engine.failed
import net.legio.eve.engine.success
import net.legio.eve.engine.toAbsoluteString
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class AuthManager(private val workspace: IWorkspace, ssoService: EveSSOService): BaseAuthManager(ssoService) {

    private val credCachePath: Path = Paths.get(workspace.cacheDirectory().toAbsoluteString(), "sso.txt")

    init {
        startup()
    }

    override fun loadSSOTokenSet(): Result<SSOTokenSet> {
        if(Files.notExists(credCachePath)) return failed("No cache file.")
        val content = Files.readAllBytes(credCachePath)
        return try {
            val tokenSet = jacksonObjectMapper().readValue<SSOTokenSet>(content)
            success(tokenSet)
        }catch (e: Exception){
            failed("Failed to deserialize token cache.", e)
        }
    }

    override fun saveSSOTokenSet(tokenSet: SSOTokenSet): Result<Unit> {
        val tempSso = Files.createTempFile(credCachePath.parent, "sso", null)
        jacksonObjectMapper().writeValue(tempSso.toFile().outputStream(), tokenSet)
        val result: Result<Unit> = try {
            tempSso.toFile().copyTo(credCachePath.toFile(), true)
            success()
        }catch (e: Exception){
            failed("Failed to store the token set.", e)
        }
        Files.deleteIfExists(tempSso)
        return result
    }

}
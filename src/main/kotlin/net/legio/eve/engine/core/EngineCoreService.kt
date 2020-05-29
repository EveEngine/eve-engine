package net.legio.eve.engine.core

import org.springframework.stereotype.Component
import java.io.IOException
import java.net.URL

@Component
internal class EngineCoreService: IEngineCoreService {
    override fun hasInternetConnection(): Boolean {
        return try{
            URL("https://www.google.com").openConnection()
            true
        }catch (e: IOException){
            false
        }
    }
}
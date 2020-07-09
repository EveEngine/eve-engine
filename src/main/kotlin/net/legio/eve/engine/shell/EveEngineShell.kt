package net.legio.eve.engine.shell

import org.jline.utils.AttributedString
import org.jline.utils.AttributedStyle
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.shell.jline.PromptProvider

@SpringBootApplication(scanBasePackages = ["net.legio.eve.engine"])
open class EveEngineShell {

    // Documentation: https://docs.spring.io/spring-shell/docs/current/reference/htmlsingle/

    @Bean
    open fun promptProvider(): PromptProvider {
        return PromptProvider { AttributedString("eve-shell:>", AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)) }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>){
            val context = SpringApplication.run(EveEngineShell::class.java, *args)
            context.beanDefinitionNames.forEach { d -> println(d) }
        }
    }

}
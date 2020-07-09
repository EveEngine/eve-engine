package net.legio.eve.engine

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope

@Bean
@Scope("singleton")
@Target(AnnotationTarget.FUNCTION)
annotation class SingletonBean {
}
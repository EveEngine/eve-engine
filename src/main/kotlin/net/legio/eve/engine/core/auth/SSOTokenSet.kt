package net.legio.eve.engine.core.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class SSOTokenSet (
        @JsonProperty("access_token")
        val accessToken: String,
        @JsonProperty("expires_in")
        val expiresIn: Int,
        @JsonProperty("token_type")
        val tokenType: String,
        @JsonProperty("refresh_token")
        val refreshToken: String
)
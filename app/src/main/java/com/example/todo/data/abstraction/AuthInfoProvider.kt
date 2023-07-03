package com.example.todo.data.abstraction

import com.example.todo.data.model.AuthInfo
import kotlinx.coroutines.flow.Flow

interface AuthInfoProvider {
    fun authInfoFlow(): Flow<AuthInfo>
    fun authInfo(): AuthInfo

}

interface AuthInfoMutableProvider: AuthInfoProvider {
    suspend fun updateAuthInfo(info: AuthInfo)
    suspend fun updateAuthToken(token: String)
    suspend fun updateRevision(revision: String)
    suspend fun clearAuthInfo()
}
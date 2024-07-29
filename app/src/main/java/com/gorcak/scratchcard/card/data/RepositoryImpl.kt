package com.gorcak.scratchcard.card.data

import com.gorcak.scratchcard.R
import com.gorcak.scratchcard.card.domain.ScratchCardValidator
import com.gorcak.scratchcard.card.domain.DataResult
import com.gorcak.scratchcard.card.domain.ErrorData
import com.gorcak.scratchcard.card.domain.Repository
import com.gorcak.scratchcard.card.domain.Storage
import com.gorcak.scratchcard.core.StringValue
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import timber.log.Timber
import java.util.UUID
import kotlin.coroutines.cancellation.CancellationException


class RepositoryImpl(
    private val storage: Storage,
    private val apiService: ApiService,
    private val scratchCardValidator: ScratchCardValidator
) : Repository {

    override suspend fun scratchCard(): DataResult<Unit> {
        return coroutineScope {
            try {
                delay((2000L..3000L).random())
                ensureActive()
                val result = UUID.randomUUID()
                storage.setScratched(result.toString())
                DataResult.Success(Unit)
            } catch (e: Exception) {
                Timber.e(e)
                if (e is CancellationException) {
                    throw e
                }
                DataResult.Error(
                    error = ErrorData(
                        exception = e,
                        message = StringValue.Resource(R.string.error_scratching)
                    )
                )
            }
        }
    }

    override suspend fun activateCard(code: String) : DataResult<Unit> {
        return coroutineScope {
            try {
                if(code.isEmpty()){
                    throw RuntimeException("Trying to activate card with empty code")
                }
                val resultCode =  apiService.getVersion(code).toVersion()
                if(scratchCardValidator.isActivationValid(resultCode)){
                    storage.setActivated(code)
                    DataResult.Success(Unit)
                } else {
                    DataResult.Error(
                        error = ErrorData(
                            message = StringValue.Resource(R.string.error_activating)
                        )
                    )
                }
            } catch (e: Exception) {
                Timber.e(e)
                if (e is CancellationException) {
                    throw e
                }
                DataResult.Error(
                    error = ErrorData(
                        exception = e,
                        message = StringValue.Resource(R.string.error_activating)
                    )
                )
            }
        }
    }

}
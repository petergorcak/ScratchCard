package com.gorcak.scratchcard

import com.gorcak.scratchcard.card.data.MemoryStorage
import com.gorcak.scratchcard.card.data.RepositoryImpl
import com.gorcak.scratchcard.card.data.toVersion
import com.gorcak.scratchcard.card.domain.ActivationValidator
import com.gorcak.scratchcard.card.domain.DataResult
import com.gorcak.scratchcard.card.domain.ScratchCardState
import com.gorcak.scratchcard.card.presentation.MainViewModel
import com.gorcak.scratchcard.card.presentation.scratch.ScratchAction
import com.gorcak.scratchcard.card.presentation.scratch.ScratchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ViewModelRepositoryTests {
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val service = FakeApiService()
    private val storage = MemoryStorage()
    private val validator = ActivationValidator()
    private val scratchCardRepository = RepositoryImpl(
        storage = storage,
        apiService = service,
        activationValidator = validator
    )

    @Test
    fun testFailedRepositoryResultForActivation() = runTest {
        val code = "12345"
        service.responseToReturn = invalidResponse
        storage.setScratched(code)
        val result = scratchCardRepository.activateCard(code)

        Assert.assertTrue(result is DataResult.Error)
    }

    @Test
    fun testSuccessRepositoryResultForActivation() = runTest {
        val code = "12345"
        service.responseToReturn = validResponse
        storage.setScratched(code)
        val result = scratchCardRepository.activateCard(code)

        Assert.assertTrue(result is DataResult.Success)
    }

    @Test
    fun testFailedViewModelActivationFromUnscratchedState() = runTest {
        val viewModel = MainViewModel(
            storage = storage,
            repository = scratchCardRepository
        )
        viewModel.activateCard()
        Assert.assertEquals(ScratchCardState.Unscratched, viewModel.state.scratchCardState)
    }

    @Test
    fun testSuccessViewModelActivationFromScratchedState() = runTest {
        val code = "12345"
        val viewModel = MainViewModel(
            storage = storage,
            repository = scratchCardRepository
        )
        service.responseToReturn = validResponse
        storage.setScratched(code)

        viewModel.activateCard()
        delay(1000)
        Assert.assertEquals(ScratchCardState.Activated(code), storage.scratchCardState.value)
        Assert.assertEquals(ScratchCardState.Activated(code), viewModel.state.scratchCardState)
    }

    @Test
    fun testFailedRepeatedScratch() = runTest {
        val viewModel = ScratchViewModel(
            storage = storage,
            repository = scratchCardRepository
        )
        service.responseToReturn = validResponse

        // first scratch
        viewModel.onAction(ScratchAction.Scratch)
        delay(5000)
        val beforeSecondScratch = storage.scratchCardState.value


        // first scratch
        viewModel.onAction(ScratchAction.Scratch)
        delay(5000)

        Assert.assertEquals(beforeSecondScratch, storage.scratchCardState.value)
        Assert.assertEquals(beforeSecondScratch, viewModel.state.scratchData)
    }

    @Test
    fun testValidatorWithValidActivationResponse() {
        val result = validator.isActivationValid(validResponse.toVersion())
        Assert.assertTrue(result)
    }

    @Test
    fun testValidatorWithInvalidActivationResponse() {
        val result = validator.isActivationValid(invalidResponse.toVersion())
        Assert.assertFalse(result)
    }
}
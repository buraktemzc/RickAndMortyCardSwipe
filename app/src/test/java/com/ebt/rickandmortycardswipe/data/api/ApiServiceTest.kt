package com.ebt.rickandmortycardswipe.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {
    private lateinit var service: ApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Test
    fun `Get characters from API and received data not null`() {
        runBlocking {
            enqueueMockResponse("characters_response.json")
            val responseBody = service.getCharacters(1).body()
            mockWebServer.takeRequest()
            assertThat(responseBody).isNotNull()
        }
    }

    @Test
    fun `Get characters from API and request path is equal as expected`() {
        runBlocking {
            enqueueMockResponse("characters_response.json")
            val responseBody = service.getCharacters(1).body()
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/character?page=1")
        }
    }

    @Test
    fun `Get characters from API and received response has correct data size`() {
        runBlocking {
            enqueueMockResponse("characters_response.json")
            val responseBody = service.getCharacters(1).body()
            val characterList = responseBody!!.characters
            assertThat(characterList?.size ?: 0).isEqualTo(20)
        }
    }

    @Test
    fun `Get characters from API and received response has correct content`() {
        runBlocking {
            enqueueMockResponse("characters_response.json")
            val responseBody = service.getCharacters(1).body()
            val characterList = responseBody!!.characters
            val character = characterList?.get(0)
            assertThat(character!!.name).isEqualTo("Rick Sanchez")
            assertThat(character.status).isEqualTo("Alive")
            assertThat(character.gender).isEqualTo("Male")
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun enqueueMockResponse(jsonFileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(jsonFileName)
        val resource = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(resource.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }
}
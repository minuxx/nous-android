package com.schopenhauer.nous.data.network

import android.util.Log
import com.schopenhauer.nous.data.CommonError.UNKNOWN
import com.schopenhauer.nous.data.network.NetworkError.CONNECT
import com.schopenhauer.nous.data.Error
import com.schopenhauer.nous.data.Result
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object ApiHandler {
	suspend fun <T : Any> safeCall(api: suspend () -> Response<T>): T {
		val response = api.invoke()
		val body = response.body()

		if (response.isSuccessful && body != null) {
			return body
		} else {
			throw ApiException(response.errorBody())
		}
	}

	fun handleException(tag: String, owner: NetworkOwner, e: Exception): Result.Failure {
		return when (e) {
			is HttpException -> {
				Log.e(tag, "HTTP: ${e.code()}, ${e.message()}")
				Result.Failure(Error.Network(CONNECT.code, CONNECT.message))
			}

			is IOException -> {
				Log.e(tag, "Network: ${e.message}")
				Result.Failure(Error.Network(CONNECT.code, CONNECT.message))
			}

			is ApiException -> {
				val error = convertErrorBody(tag, owner, e.body)
				Result.Failure(error)
			}

			else -> {
				Log.e(tag, "Unknown: ${e.message}")
				Result.Failure(Error.Common(UNKNOWN))
			}
		}
	}

	private fun convertErrorBody(tag: String, owner: NetworkOwner, errorBody: ResponseBody?): Error {
		return try {
			val jsonObj = JSONObject(errorBody.toString())
			val code = jsonObj.getString(owner.codeKey)
			val message = jsonObj.getString(owner.messageKey)

			Error.Network(code, message)
		} catch (e: JSONException) {
			Log.e(tag, "Json Parsing: ${e.message}")
			Error.Common(UNKNOWN)
		} catch (e: Exception) {
			Log.e(tag, "Unknown: ${e.message}")
			Error.Common(UNKNOWN)
		}
	}
}

class ApiException(val body: ResponseBody?) : Exception()
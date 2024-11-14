// FoodLensModule.kt
package com.frontend

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.doinglab.foodlens.sdk.core.FoodLensCore
import com.doinglab.foodlens.sdk.core.RecognitionResultHandler
import com.doinglab.foodlens.sdk.core.error.BaseError
import com.doinglab.foodlens.sdk.core.model.result.RecognitionResult
import com.doinglab.foodlens.sdk.core.type.FoodLensType
import android.util.Base64

class FoodLensModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private lateinit var recognitionResult: RecognitionResult

    override fun getName(): String {
        return "FoodLensModule"  // JavaScript에서 사용할 이름
    }

    //Create FoodLens Service
    private val foodLensCoreService by lazy {
        FoodLensCore.createFoodLensService(reactContext, FoodLensType.FoodLens)
    }

    private fun startFoodLensCore(byteData: ByteArray) {
        foodLensCoreService.predict(byteData, object : RecognitionResultHandler {
            override fun onSuccess(result: RecognitionResult?) {
                result?.let {
                    setRecognitionResultData(result)
                }
            }

            override fun onError(errorReason: BaseError?) {
                // FoodLens 시작 오류 시 코드 필요
            }
        })
    }

    private fun setRecognitionResultData(resultData: RecognitionResult) {
        recognitionResult = resultData
        var json = recognitionResult.toJSONString()
    }


    @ReactMethod
    fun recognizeFood(imageData: String, promise: Promise) {
        try {
            val byteData = Base64.decode(imageData, Base64.DEFAULT)
            val foodLensCoreService = FoodLensCore.createFoodLensService(this.reactApplicationContext, FoodLensType.FoodLens)

            foodLensCoreService.predict(byteData, object : RecognitionResultHandler {
                override fun onSuccess(result: RecognitionResult?) {
                    val jsonResult = result?.toJSONString() ?: "{}"
                    promise.resolve(jsonResult) // 성공 시 Promise 반환
                }

                override fun onError(errorReason: BaseError?) {
                    promise.reject("Recognition Error", errorReason?.getMessage())
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            promise.reject("Native Code Exception", e) // 오류 시 Promise 반환
        }
    }
}

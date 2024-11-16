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
//import com.doinglab.foodlens.sdk.core.type.FoodLensType
//import com.doinglab.foodlens.sdk.core.type.NutritionRetrieveOption
import com.doinglab.foodlens.sdk.core.type.*
import android.util.Base64
import org.json.JSONObject

class FoodLensModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private lateinit var recognitionResult: RecognitionResult

    override fun getName(): String {
        return "FoodLensModule"  // JavaScript에서 사용할 이름
    }

    //Create FoodLens Service
    private val foodLensCoreService by lazy {
//        FoodLensCore.createFoodLensService(reactContext, FoodLensType.FoodLens)
        FoodLensCore.createFoodLensService(reactContext, FoodLensType.FoodLens).apply {
            setNutritionRetrieveOption(NutritionRetrieveOption.TOP1_NUTRITION_ONLY)
        }
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
                    if (result == null) {
                        promise.reject("Recognition Error", "No result from FoodLens")
                        return
                    }

                    // Extract the first candidate's carbohydrate, protein, fat, and energy values
                    val candidate = result.foods.firstOrNull()?.candidates?.firstOrNull()
                    if (candidate != null) {
                        val nutritionData = JSONObject().apply {
                            put("foodName", candidate.fullFoodName)
                            put("carbohydrate", candidate.carbohydrate)
                            put("protein", candidate.protein)
                            put("fat", candidate.fat)
                            put("energy", candidate.energy)
                        }
                        promise.resolve(nutritionData.toString())
                    } else {
                        promise.reject("Recognition Error", "No nutrition data available")
                    }
                }

                override fun onError(errorReason: BaseError?) {
                    promise.reject("Recognition Error", errorReason?.getMessage())
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            promise.reject("Native Code Exception", e) // 오류 시 Promaise 반환
        }
    }
}

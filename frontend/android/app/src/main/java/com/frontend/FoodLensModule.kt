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

                    val candidate = result.foods.firstOrNull()?.candidates?.firstOrNull()
                    if (candidate != null) {
//                        val nutritionData = JSONObject().apply {
//                            put("foodName", candidate.fullFoodName) // 음식 이름
//                            put("carbohydrate", candidate.carbohydrate) // 탄수화물
//                            put("protein", candidate.protein) // 프로틴
//                            put("fat", candidate.fat) // 지방
//                            put("energy", candidate.energy) // 열량(칼로리)
//                            put("sodium", candidate.sodium) // 나트륨
//                            put("sugar", candidate.totalSugar) // 당
//                            put("transFat", candidate.transFattyAcid) // 트랜스지방
//                            put("saturatedFat", candidate.saturatedFattyAcid) // 포화지방
//                            put("cholesterol", candidate.cholesterol) // 콜레스트롤
//                        }
                        const nutritionData = {
                            calorie: candidate.energy ?? 0, // energy → calorie (fallback to 0 if undefined)
                            carbo: candidate.carbohydrate ?? 0, // carbohydrate → carbo (fallback to 0 if undefined)
                            protein: candidate.protein ?? 0, // 그대로
                            fat: candidate.fat ?? 0, // 그대로
                            sodium: candidate.sodium ?? 0,
                            sugar: candidate.sugar ?? 0,
                            transFat: candidate.transFat ?? 0,
                            saturatedFat: candidate.saturatedFat ?? 0,
                            cholesterol: candidate.cholesterol ?? 0
                        };
                        promise.resolve(nutritionData)
//                        promise.resolve(nutritionData.toString())
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

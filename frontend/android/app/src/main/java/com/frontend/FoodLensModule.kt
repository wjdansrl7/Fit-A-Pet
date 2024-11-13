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

    override fun getName(): String {
        return "FoodLensModule"  // JavaScript에서 사용할 이름
    }

    @ReactMethod
    fun recognizeFood(imageData: String, promise: Promise) {
        val byteData = Base64.decode(imageData, Base64.DEFAULT)

        // FoodLens SDK 초기화 및 인식 요청
        val foodLensCoreService = FoodLensCore.createFoodLensService(reactApplicationContext, FoodLensType.FoodLens)
        foodLensCoreService.predict(byteData, object : RecognitionResultHandler {
            override fun onSuccess(result: RecognitionResult?) {
                // 인식 결과를 JSON으로 변환하여 전달
                val jsonResult = result?.toJSONString()
                promise.resolve(jsonResult)
            }

            override fun onError(errorReason: BaseError?) {
                promise.reject("Recognition Error", errorReason?.getMessage())
            }
        })
    }
}

package com.frontend

import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate
import com.doinglab.foodlens.sdk.core.FoodLensCore
import com.doinglab.foodlens.sdk.core.RecognitionResultHandler
import com.doinglab.foodlens.sdk.core.error.BaseError
import com.doinglab.foodlens.sdk.core.model.result.RecognitionResult
import com.doinglab.foodlens.sdk.core.type.*
import android.widget.Toast
import android.os.Bundle;

import android.graphics.BitmapFactory
import android.util.Base64
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.Promise
import java.io.ByteArrayOutputStream

class MainActivity : ReactActivity() {
  private lateinit var recognitionResult: RecognitionResult

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  override fun getMainComponentName(): String = "frontend"
  
  /**
   * Returns the instance of the [ReactActivityDelegate]. We use [DefaultReactActivityDelegate]
   * which allows you to enable New Architecture with a single boolean flags [fabricEnabled]
   */
  override fun createReactActivityDelegate(): ReactActivityDelegate =
      DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)
  

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(null)
  }

  //Create FoodLens Service
  private val foodLensCoreService by lazy {
    FoodLensCore.createFoodLensService(this, FoodLensType.FoodLens)
  }

  private fun startFoodLensCore(byteData: ByteArray) {
    foodLensCoreService.predict(byteData, object : RecognitionResultHandler {
      override fun onSuccess(result: RecognitionResult?) {
        result?.let {
          setRecognitionResultData(result)
        }
      }

      override fun onError(errorReason: BaseError?) {
        Toast.makeText(this@MainActivity, errorReason?.getMessage(), Toast.LENGTH_SHORT).show()
      }
    })
  }

  private fun setRecognitionResultData(resultData: RecognitionResult) {
    recognitionResult = resultData
    var json = recognitionResult.toJSONString()
  }

  @ReactMethod
  fun recognizeFood(imageData: String, promise: Promise) {
    val byteData = Base64.decode(imageData, Base64.DEFAULT)

    // FoodLens SDK 초기화 및 인식 요청
    val foodLensCoreService = FoodLensCore.createFoodLensService(this, FoodLensType.FoodLens)
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

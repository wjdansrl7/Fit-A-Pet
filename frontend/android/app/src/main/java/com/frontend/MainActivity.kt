package com.frontend

import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate
import dev.matinzd.healthconnect.permissions.HealthConnectPermissionDelegate
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import android.widget.Button
import android.os.Bundle
import com.frontend.R


class MainActivity : ReactActivity() {

  // Data Layer API를 사용하기 위한 DataClient 객체 선언
  private lateinit var dataClient: DataClient

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  override fun getMainComponentName(): String = "frontend"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(null)
    // In order to handle permission contract results, we need to set the permission delegate.
    HealthConnectPermissionDelegate.setPermissionDelegate(this)

    // Data Layer API 초기화
    dataClient = Wearable.getDataClient(this)

    // 버튼을 찾아서 클릭 이벤트 설정
    val sendDataButton: Button = findViewById(R.id.sendDataButton)
    sendDataButton.setOnClickListener {
      // 버튼이 클릭되면 sendDataToWear 함수를 호출하여 데이터를 전송
      sendDataToWear()
    }
  }

  // Wear OS로 데이터 전송하는 함수
  private fun sendDataToWear() {
    // DataMap을 통해 전송할 데이터를 설정 ("/message_path"는 데이터의 식별 경로)
    val putDataReq = PutDataMapRequest.create("/message_path").apply {
      // "message"라는 키로 "Hello Wear!" 값을 추가하여 전송할 데이터 설정
      dataMap.putString("message", "Hello Wear!")
    }.asPutDataRequest()

    // 데이터 전송 수행
    dataClient.putDataItem(putDataReq).addOnSuccessListener {
      // 성공적으로 전송된 경우 (필요 시 추가 작업 가능)
    }.addOnFailureListener {
      // 전송 실패 시 오류 처리 (필요 시 추가 작업 가능)
    }
  }

  /**
   * Returns the instance of the [ReactActivityDelegate]. We use [DefaultReactActivityDelegate]
   * which allows you to enable New Architecture with a single boolean flags [fabricEnabled]
   */
  override fun createReactActivityDelegate(): ReactActivityDelegate =
    DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)
}

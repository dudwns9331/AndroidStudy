package YJ.StopWatch.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var lap = 1
    private var lap_count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener { // fab는 버튼의 아이디이다. fab는 별다른 선언없이 사용할 수 있도록 되어있다. ClickListener이므로 클릭할 때 마다 실행되는 코드

            isRunning = !isRunning // 실행되어지고 있나? isRunning(boolean)값. 버튼이 눌렸을 때 true의 값으로 바꾸어준다.

            if(isRunning) {
                start()
            } else {
                pause()
            }
        }

        resetButton.setOnClickListener() {
            reset() // 리셋버튼이 눌렸을 때 실행되는 클릭리스너
        }

        lapButton.setOnClickListener() {
            recordLapTime() // 랩타임 버튼이 눌렸을 때 실행되는 클릭리스너
        }
    }

    private fun pause() {
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp) // 시작버튼의 이미지를 바꾼다.

        timerTask?.cancel() // Timer class의 cancel()함수를 호출한다. pause버튼이 눌렸을 때 시간이 멈추도록 한다.
    }

    private fun start() {
        fab.setImageResource(R.drawable.ic_pause_black_24dp) // 이미지를 정지버튼으로 바꾼다.

        // timer함수의 기간을 10 = 1초를 지정해주고 timerTaskdp 저장한다.
        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread { // runOnUiThread를 통해서 스레드의 이용을 통해 시간이 계속해서 변하는 작업을 해준다.
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun reset() {
        timerTask?.cancel()

        //모든 변수 초기화
        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"

        //모든 랩타임을 제거
        labLayout.removeAllViews() // labLayout의 아이디를 가진 레이아웃에 모든 뷰를 제거한다.
        lap =1
        lap_count = 0
    }

    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAB : ${lapTime/ 100}.${lapTime % 100}"

        // 맨 위에 랩타임 추가
        labLayout.addView(textView, lap_count)
        lap++
        lap_count++
    }
}

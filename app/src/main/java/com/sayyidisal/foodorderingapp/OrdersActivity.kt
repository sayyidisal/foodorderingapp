package com.sayyidisal.foodorderingapp

import android.content.*
import android.content.res.AssetManager
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.iwgang.countdownview.CountdownView
import com.sayyidisal.foodorderingapp.ingredient.IngredientActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_orders.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class OrdersActivity : AppCompatActivity() {
    var quantityaddon: ArrayList<String> = ArrayList()
    var titleaddon: ArrayList<String> = ArrayList()
    private var products : ArrayList<Orders> = ArrayList()
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    var listOfproduct: ArrayList<product> = ArrayList()
    private val LIMIT_TIME: Long = 5*10000
    var isStart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        init()

        ingre.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, IngredientActivity::class.java)
            startActivity(intent)
        })
        fun AssetManager.readFile(fileName: String) = open(fileName)
            .bufferedReader()
            .use { it.readText() }
        val jsonString = applicationContext.assets.readFile("response.json")

        acceptbtn.setOnClickListener {
            countdown_view.stop()
            reset()
        }
//        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)

        try {
            val obj = JSONObject(jsonString)
            val productArray = obj.getJSONArray("data")
            System.out.println("kasbkdbasdkf" + productArray)
            for (i in 0 until productArray.length()){
                val productDetail = productArray.getJSONObject(i)
                val productss = product()
                productss.productname = productDetail.getString("title")
                productss.productqty = productDetail.getInt("quantity")
                productss.productCreatedat = productDetail.getString("created_at")
                productss.productExpiredat = productDetail.getString("expired_at")
                val addon = productDetail.getJSONArray("addon")
                for (j in 0 until addon.length()){
                    val addonDetail = addon.getJSONObject(j)
                    titleaddon.add(addonDetail.getString("title"))
                    quantityaddon.add(addonDetail.getString("quantity"))
                    productss.productaddon = addonDetail.getString("title")
                    productss.productqtyaddon = addonDetail.getString("quantity")
                }
                listOfproduct!!.add(productss)


                val stringDate = productDetail.getString("created_at")
                val stringDateFinish = productDetail.getString("expired_at")
                var inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                var outputFormat: DateFormat = SimpleDateFormat("'Date : 'dd-MM-yyyy\n'Time : 'KK:mm a")
                System.out.println( "dayiwegjhabjf" +outputFormat.format(inputFormat.parse(stringDate)));
                timeorders.setText(outputFormat.format(inputFormat.parse(stringDate)).substring(24))

                var min: Long = 0
                val difference: Long
                try {
                    val simpleDateFormat =
                            SimpleDateFormat("hh:mm aa") // for 12-hour system, hh should be used instead of HH
                    // There is no minute different between the two, only 8 hours difference. We are not considering Date, So minute will always remain 0
                    val date1: Date = simpleDateFormat.parse(outputFormat.format(inputFormat.parse(stringDate)).substring(24))
                    val date2: Date = simpleDateFormat.parse(outputFormat.format(inputFormat.parse(stringDateFinish)).substring(24))
                    difference = (date2.getTime() - date1.getTime()) / 1000
                    val hours = difference % (24 * 3600) / 3600 // Calculating Hours
                    val minute =
                            difference % 3600 / 60 // Calculating minutes if there is any minutes difference
                    min =
                            minute + hours * 60 // This will be our final minutes. Multiplying by 60 as 1 hour contains 60 mins

                    System.out.println("kajsbdkfbkasdb" + min)

                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        } catch (e: JSONException){
            e.printStackTrace()
        }
        mRecyclerView = findViewById(R.id.recyclerview)
        var mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView!!.layoutManager = mLayoutManager
        mAdapter = CustomAdapter(listOfproduct)
        mRecyclerView!!.adapter = mAdapter
    }

    private fun init(){
        Paper.init(this)
        isStart = Paper.book().read(IS_START_KEY,false)
        if (isStart){
            countdown_view.start(LIMIT_TIME)
            checkTime()
        }
        countdown_view.start(LIMIT_TIME)
        countdown_view.setOnCountdownEndListener {
            Toast.makeText(this, "Finish!", Toast.LENGTH_SHORT).show()
            reset()
        }
        countdown_view.setOnCountdownIntervalListener(1000, object : CountdownView.OnCountdownIntervalListener{
            override fun onInterval(cv: CountdownView?, remainTime: Long) {
                Log.d("TIMER", ""+remainTime)
            }
        })
    }

    override fun onStop() {
        Paper.book().write(TIME_REMAIN, countdown_view.remainTime)
        Paper.book().write(LAST_TIME_SAVED_KEY,System.currentTimeMillis())
        super.onStop()
    }

    private fun checkTime() {
        val currentTime = System.currentTimeMillis()
        val lastTimeSaved: Long = Paper.book().read<Long>(LAST_TIME_SAVED_KEY, 0).toLong()
        val timeRemain: Long = Paper.book().read(TIME_REMAIN, 0).toLong()
        val result = timeRemain + (lastTimeSaved - currentTime)
        if (result > 0){
            countdown_view.start(result)
            acceptbtn.isEnabled = false
        }else{
            countdown_view.stop()
            reset()
        }
    }

    private fun reset() {
        Paper.book().delete(IS_START_KEY)
        Paper.book().delete(LAST_TIME_SAVED_KEY)
        Paper.book().delete(TIME_REMAIN)
        isStart = false
        acceptbtn.isEnabled = false
    }


    companion object {
        private const val IS_START_KEY = "IS_START"
        private const val LAST_TIME_SAVED_KEY = "LAST_TIME_SAVED"
        private const val TIME_REMAIN = "TIME_REMAIN"
    }

}
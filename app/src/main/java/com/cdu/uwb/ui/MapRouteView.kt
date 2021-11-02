package com.cdu.uwb.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.cdu.uwb.R
import com.cdu.uwb.data.Coordinate
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.google.gson.reflect.TypeToken
import java.util.zip.Inflater
import kotlin.concurrent.thread

//地图界面控件
class MapRouteView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    //companion object只用来存放TAG这样的已经确定的数值
    companion object {
        @JvmStatic
        val TAG = "MapRouteView"
    }

    //全局变量放在此处，val不需要private
    //其中一个手环的路线颜色
    private lateinit var mRoutePaint: Paint

    //地图的线条颜色
    private lateinit var mMapPaint: Paint
    private lateinit var mPositionPaint: Paint
    private lateinit var mDistancePaint: Paint

    //存放从json中获取的数据的对象形式数据
    private var mCoordinate = ArrayList<Coordinate>()

    /**
     * 通过键值对的方式存放所有点的所有坐标（HashMap）
     * HashMap<FloatArray, Int>()
     *
     */
//    private var allIdCoordinate = HashMap<FloatArray, Int>()

    /**
     * 使用lateinit是说初始化的时机由我们自行决定，并不会自动赋值为null
     * 判定是否初始化的语句： this::xx.isInitialized
     */
    //这是其中一个手环的所有坐标数组，放入allIdCoordinate中，目前不需要实现，因为我们的实际应用只需要一个人的路线
    private lateinit var mIdFloatArray: FloatArray

    //这是地图的所有坐标数组，希望外界传入
    private lateinit var mMapFloatArray: FloatArray

    //使用者的位置
    private var mPositionPoint = floatArrayOf(300f, 300f)

    private var mOnRedrawFinishedListener: OnRedrawFinishedListener? = null

    //这里获取所需要的数据
    init {
        initPaint()
        parseJSONWithGSON()
    }

    //进行绘制
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        canvas!!.translate(100f, 100f)
//        canvas.scale(2f, 2f)
        //先绘制地图，先判定是否已经初始化，因为我们目前没有地图数据
        if (this::mMapFloatArray.isInitialized) {
            canvas.drawLines(mMapFloatArray, mMapPaint)
        }
        //其中一个手环的路线
        if (this::mIdFloatArray.isInitialized) {
            canvas.drawLines(mIdFloatArray, mRoutePaint)
        }
        //绘制使用人的位置点
        canvas.drawCircle(mPositionPoint[0], mPositionPoint[1], 10f, mPositionPaint)
        canvas.drawLine(
            mIdFloatArray[0],
            mIdFloatArray[1],
            mPositionPoint[0],
            mPositionPoint[1],
            mDistancePaint
        )

    }

    /**
     * 给外界设置数据的接口
     */
    fun setMapData(mapArray: FloatArray) {
        this.mMapFloatArray = mapArray
        invalidate()
    }

    fun setIdData(idArray: FloatArray) {
        this.mIdFloatArray = idArray
        invalidate()
    }

    fun setData(mapArray: FloatArray, idArray: FloatArray) {
        this.mMapFloatArray = mapArray
        this.mIdFloatArray = idArray
        invalidate()
    }

    //毫无意义的监听器
    fun setOnRedrawFinishedListener(listener: OnRedrawFinishedListener) {
        this.mOnRedrawFinishedListener = listener
    }

    interface OnRedrawFinishedListener {
        fun onRedrawFinished()
    }

    //初始化画笔
    private fun initPaint() {
        //其中一个手环的路线颜色
        mRoutePaint = Paint()
        mRoutePaint.run {
            color = Color.BLUE
            strokeWidth = 5f
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        //地图的线条颜色
        mMapPaint = Paint()
        mMapPaint.run {
            color = Color.GREEN
            strokeWidth = 5f
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        //用户位置的圆点样式
        mPositionPaint = Paint()
        mPositionPaint.run {
            color = Color.RED
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        mDistancePaint = Paint()
        mDistancePaint.run {
            color = Color.MAGENTA
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 10f
            isAntiAlias = true
            pathEffect = DashPathEffect(floatArrayOf(4f, 4f), 0f)
        }
    }

    /**
     *这部分存在较多问题
     * 目前只做了一个人的路径，但是我们的数据并没有一个人的完整数据，
     * 而是按顺序将所有人的点放入了json文件中，如果中途有人退出或进入
     * 我们的文件数据的顺序可能就会被捣乱
     * 目前是将所有文件的第一个数据作为一个人的点，暂做测试
     *
     * 注意吼：GSON只能使用2.8.5版本，新版本TypeToken不能使用
     *
     * 因为目前是从大量json文件中获取数据，所以我们先直接全部读取完
     * 步骤：1. 挨个解析json，每个json都有n个节点的该时间的坐标，唯一的不同是id
     * 2. 挨个查看id，然后将其坐标放入mIdFloatArray中（plus函数），
     * 3. mIdFloatArray作为值，id作为键放入allIdCoordinate中，然后将mIdFloatArray清空，然后重复2，3，
     * 直到该文件的所有内容读取完，然后接下一个（分组进行读取效率会更高，但是这是在我们本地数据的基础上进行的绘制，实际情况会一次给完数据点，所以不用优化
     * 4. 这样我们就得到了拥有所有id的所有点的一个map集合，然后以id为据，为其分配画笔颜色，再以此绘制路线（使用多线程效率会更好）
     * （一个用户上只有一个人的路线，所以可能这里也不用那么麻烦，所有人的路线会在PC端展示，不是我们考虑的）
     */


    /**
     * 解析本地Json数据
     */
    private fun parseJSONWithGSON() {
        //以下是解析代码，放在你想放的地方，注意不要放在主线程
        //目前暂不做线程优化
        try {
            /**
             *  读取前二十个
             *  因为目前处理的数据都是本地的，而且文件名是由1到n，所以我们采用了循环来读取json数据
             *  TODO: 最佳方案应该是从服务器直接获取，然后直接绘制
             */
            for (p in 1..20) {
                context.assets.open("$p.json").use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val dataType = object : TypeToken<List<Coordinate>>() {}.type
                        //解析
                        val dataList: List<Coordinate> = Gson().fromJson(jsonReader, dataType)
                        //将文件的第一个点的坐标以对象的形式放入我们的list中
                        mCoordinate.add(dataList[0])
                        //查看所读取的所有坐标
//                        for (point in dataList) {
//                            Log.d(TAG, "id is ${point.id}")
//                            Log.d(TAG, "x is ${point.x}")
//                            Log.d(TAG, "y is ${point.y}")
//                                    mCoordinate.add(point)
//                        }
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
        }
        //由于drawlines是每四个绘制一次，所以我们令最开始的两个为第一个点
        mIdFloatArray = floatArrayOf((mCoordinate[0].x) * 100, (mCoordinate[0].y) * 100)
        //查看第一个的所有坐标，并为mFloatArray赋值
        for (i in mCoordinate) {
            Log.d(TAG, "id is ${i.id}")
            Log.d(TAG, "x is ${i.x}")
            Log.d(TAG, "y is ${i.y}")
            Log.d(TAG, "x is ${(i.x) * 100}")
            Log.d(TAG, "y is ${(i.y) * 100}")
            /**
             * 来自drawLines的实现原理
             * This is logically the same as
             * drawing the array as follows: drawLine(pts[0], pts[1], pts[2], pts[3]) followed by
             * drawLine(pts[4], pts[5], pts[6], pts[7]) and so on.
             * 因为他是每四个进行一次绘制，所以我们赋值两次，令第一组的结尾变为第二组的开头
             */
            mIdFloatArray = mIdFloatArray.plus((i.x) * 100)
            mIdFloatArray = mIdFloatArray.plus((i.y) * 100)
            mIdFloatArray = mIdFloatArray.plus((i.x) * 100)
            mIdFloatArray = mIdFloatArray.plus((i.y) * 100)
        }
        //查看该线段的所有点
        for (i in mIdFloatArray) {
            println(i)
        }
    }
}
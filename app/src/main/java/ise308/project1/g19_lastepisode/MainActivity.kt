package ise308.project1.g19_lastepisode

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ise308.project1.g19_lastepisode.fragment.ListSeriesFragment
import ise308.project1.g19_lastepisode.fragment.SeriesFragment
import ise308.project1.g19_lastepisode.fragment.ShowSeriesFragment
import ise308.project1.g19_lastepisode.util.JSONSerializer
import ise308.project1.g19_lastepisode.util.TvSeries

class MainActivity : AppCompatActivity() {


    private var mSerializer: JSONSerializer? = null
    private var seriesList: ArrayList<TvSeries>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        // In this code,
        // We are creating main fragment
        val fragment = ListSeriesFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_holder, fragment)
       // transaction.addToBackStack("main")
        transaction.commit()


        // What's happening when user clicked the FAB button.
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->


            // Creating fragment
            val fragment = SeriesFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_holder, fragment)


            // This code is very important for back key presses.
            // When we open a fragment with this code,
            // if the user presses the android back button,
            // the application returns to the main fragment.
            transaction.addToBackStack(null)

            transaction.commit() // opening fragment
        }



        jsonOpener()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    // In this function,
    // We are opening ShowSeries Fragment
    // With RecyclerView positionID value
    fun showTVSeries(positionID: Int) {

        val seriesID = Bundle() // Creating Bundle
        seriesID.putInt("positionID", positionID) // Putting position value inside of bundle
        val fragment = ShowSeriesFragment()
        fragment.arguments = seriesID // sending positionid value to fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    // Creating new TV Series
    fun createNewSeries(addSeries: TvSeries) {
        seriesList!!.add(addSeries)
        Log.i("@@@@@@@@@@@@ : ", addSeries.toString())
        saveSeries()
    }

    // Deleting Selected TV Series
    fun deleteSeries(seriesPosition: Int) {
        seriesList!!.removeAt(seriesPosition)
        Log.i("@@@@@@@@@@@@ : ", seriesPosition.toString())
        saveSeries()
    }

    // Opening edit TV Series
    fun editSeries(seriesPosition: Int) {

        val seriesID = Bundle() // Creating Bundle
        seriesID.putInt("positionID", seriesPosition) // Putting position value inside of bundle
        val fragment = SeriesFragment()
        fragment.arguments = seriesID // sending positionid value to fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    // Edit values inside of JSON File
    fun editedSeries(addSeries: TvSeries,seriesPosition: Int){
        seriesList!!.set(seriesPosition, addSeries)
        saveSeries()
    }



    // Saving TV series to JSON file
    private fun saveSeries() {
        try {
            // Save the note into JSON File
            mSerializer!!.save(this.seriesList!!)

        } catch (e: Exception) {
            Log.e("Error Saving Notes", "", e)
        }
    }


    // When user press android back button
    override fun onBackPressed() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) { //if backstack contain any fragment than pop it
            fm.popBackStack()
        } else {                         // Close Application
            super.onBackPressed()
        }
    }


    fun jsonOpener(){
        // Creating/Opening G19 JSON File
        mSerializer = JSONSerializer("G19.json", applicationContext)

        try {
            seriesList = mSerializer!!.load() // Loading Tv Series Details
        } catch (e: Exception) {
            seriesList = ArrayList()
            Log.e("Error loading notes: ", "", e)
        }
    }

}
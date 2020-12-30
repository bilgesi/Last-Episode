package ise308.project1.g19_lastepisode.fragment



import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ise308.project1.g19_lastepisode.MainActivity
import ise308.project1.g19_lastepisode.R
import ise308.project1.g19_lastepisode.util.JSONSerializer
import ise308.project1.g19_lastepisode.util.TvSeries


class SeriesFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {

        fun newInstance(): SeriesFragment {
            return SeriesFragment()
        }
    }

    // Variable Names
    lateinit var seriesTitle : EditText
    lateinit var seriesGenre : Spinner
    lateinit var selectedGenre : String
    lateinit var seriesLastSeason : EditText
    lateinit var seriesLastEpisode : EditText
    lateinit var cbFinished : CheckBox
    lateinit var btnAdd : Button
    lateinit var selectedGenreID : String


    private var mSerializer: JSONSerializer? = null
    private var seriesList: ArrayList<TvSeries>? = null
    private var series : TvSeries? = null


        // Creating Fragment
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.content_add, container, false)

            // Getting Value from MainActivity
        val positionID = arguments?.getInt("positionID")

        val activity = activity as MainActivity

            // ActionBar
        activity.setSupportActionBar(view.findViewById(R.id.toolbar))
        activity.supportActionBar?.setTitle(getString(R.string.addNew)) // Actionbar Title

        btnAdd = view.findViewById(R.id.seriesAdd)
        seriesTitle = view.findViewById(R.id.seriesName)
        seriesLastSeason = view.findViewById(R.id.seriesSeason)
        seriesLastEpisode = view.findViewById(R.id.seriesEpisode)
        cbFinished = view.findViewById(R.id.isFinished)
        seriesGenre = view.findViewById(R.id.spinnerGenre)

            // Creating Spinner for Genres
        val adapter = ArrayAdapter.createFromResource(
                activity,
                R.array.genres,
                android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        seriesGenre.adapter = adapter
        seriesGenre.onItemSelectedListener = this

            // Checking argument for null or not.
            // if main activity send any data to fragment then argument is not null
        if(arguments != null){
                btnAdd.setText(getString(R.string.editSeries))
                jsonOpener()
                selectedSeries(seriesList!![positionID!!])
                seriesTitle.setText(series!!.sName)
                seriesLastSeason.setText(series!!.sLastSeason)
                seriesLastEpisode.setText(series!!.sLastEpisode)
                cbFinished.isChecked = series!!.isFinished
                seriesGenre.setSelection((series!!.sGenreId)!!.toInt())

        }

        btnAdd.setOnClickListener {
            if(arguments != null){
                editSeries(positionID!!)
                activity.onBackPressed()
            } else {
                addSeries()
                activity.onBackPressed()
            }
        }
        return view
    }



    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented")
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val text: String = parent?.getItemAtPosition(position).toString() // Getting Selected Spinner Value
        val genreid: String = parent?.getItemIdAtPosition(position).toString() // Getting Selected Spinner ID

        selectedGenre = text
        selectedGenreID = genreid
    }

    fun addSeries(){

        // Create a new series
        val newSeries = TvSeries()


        newSeries.sName = seriesTitle.text.toString()
        newSeries.sLastSeason = seriesLastSeason.text.toString()
        newSeries.sLastEpisode = seriesLastEpisode.text.toString()
        newSeries.sGenre = selectedGenre
        // Checkbox is Checked?
        newSeries.isFinished= cbFinished.isChecked
        newSeries.sGenreId = selectedGenreID

        // Calling a function from the Main Activity
        (activity as MainActivity).createNewSeries(newSeries)
    }


    fun editSeries(positionID: Int){

        // Editing series
        val newSeries = TvSeries()


        newSeries.sName = seriesTitle.text.toString()
        newSeries.sLastSeason = seriesLastSeason.text.toString()
        newSeries.sLastEpisode = seriesLastEpisode.text.toString()
        newSeries.sGenre = selectedGenre
        // Checkbox is Checked?
        newSeries.isFinished= cbFinished.isChecked
        newSeries.sGenreId = selectedGenreID

        // Calling a function from the Main Activity
        (activity as MainActivity).editedSeries(newSeries, positionID!!)
    }


    fun jsonOpener(){
        // Creating/Opening G19 JSON File
        val activity = activity as MainActivity
        mSerializer = JSONSerializer("G19.json", activity)

        try {
            seriesList = mSerializer!!.load() // Loading Series Details
        } catch (e: Exception) {
            seriesList = ArrayList()
            Log.e("Error loading notes: ", "", e)
        }
    }


    fun selectedSeries(seriesSelected: TvSeries) {
        series = seriesSelected
    }


}
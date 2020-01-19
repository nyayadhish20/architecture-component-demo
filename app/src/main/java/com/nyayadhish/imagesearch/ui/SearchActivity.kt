package com.nyayadhish.imagesearch.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyayadhish.imagesearch.R
import com.nyayadhish.imagesearch.data.Items
import com.nyayadhish.imagesearch.debugLog
import com.nyayadhish.imagesearch.dihelper.DIHelper
import com.nyayadhish.imagesearch.viewmodel.ResultListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class SearchActivity : AppCompatActivity() {

    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var recentSearchAdapte: RecentSearchAdapte
    private var mList: ArrayList<Items> = ArrayList()
    private var startIndex = "1"
    private lateinit var viewModel: ResultListViewModel
    private var currentGridSize = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initialiseUI()
        iv_search.setOnClickListener {
            makeAPICall(startIndex)
        }

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty()!!) {
                    mList.clear()
                    viewModel.clearSearchResults()
                    searchResultAdapter.notifyDataSetChanged()
                }
            }

        })
    }

    /**
     * Initialize the UI
     */
    private fun initialiseUI() {
        val factory = DIHelper.provideSearchViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(ResultListViewModel::class.java)
        //makeAPICall(startIndex)
        setupRecyclerView()
        showRecentSearches()
        rv_search_results.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(rv_search_results.layoutManager as GridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                debugLog("page = $page" + "Total Items = " + totalItemsCount)
                makeAPICall((totalItemsCount + 1).toString())
            }

            override fun sendLastVisibleItemPosition(
                lastCompletelyVisibleItemPosition: Int,
                lastVisibleItemPosition: Int
            ) {
                //debugLog("SecondMethod => $lastVisibleItemPosition")
            }
        })
    }

    /**
     * Recent Searched adapter setup
     */
    private fun showRecentSearches() {
        val recentSearch = getPreferences()
        if (recentSearch != null) {
            rv_recent_search.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recentSearchAdapte = object : RecentSearchAdapte(getPreferences()) {
                override fun onSearchItemClick(searchItem: String) {
                    viewModel.clearSearchResults()
                    searchResultAdapter.notifyDataSetChanged()
                    et_search.setText(searchItem)
                    makeAPICall(startIndex)
                }
            }
            rv_recent_search.adapter = recentSearchAdapte
            recentSearchAdapte.notifyDataSetChanged()
        }
    }

    /**
     * Set up grid recycler view for search result.
     */
    private fun setupRecyclerView() {
        rv_search_results.layoutManager = GridLayoutManager(this, currentGridSize)
        searchResultAdapter = object : SearchResultAdapter(mList) {
            override fun onImageClick(
                image: Items,
                ivImage: ImageView
            ) {
                val activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@SearchActivity,
                        ivImage,
                        "imageMain"
                    )
                val intent = Intent(this@SearchActivity, ImageDetailsActivity::class.java)
                intent.putExtra(ImageDetailsActivity.KEY_IMAGE_URL, image.link)
                ContextCompat.startActivity(
                    this@SearchActivity,
                    intent,
                    activityOptionsCompat.toBundle()
                )
            }

        }
        rv_search_results.adapter = searchResultAdapter
    }

    /**
     * Make API call with respect to the parameter and observe the livedata.
     */
    private fun makeAPICall(startIndex: String) {
        storeRecentSearch()
        viewModel.getSearchResult(et_search.text.toString(), startIndex).observe(this, Observer {
            mList.addAll(it)
            searchResultAdapter.notifyDataSetChanged()
        })
    }

    /**
     * Store the recent searches in preferences
     */
    private fun storeRecentSearch() {
        var recentSearchList = getPreferences()
        if(recentSearchList == null) {
            recentSearchList = HashSet<String>()
        }
        recentSearchList.add(et_search.text.toString())
        addToPreferences(recentSearchList)
        showRecentSearches()
    }


    /**
     * Methos to store hashSet in preferences.
     */
    private fun addToPreferences(set: MutableSet<String>?) {
        val editor = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).edit()
        editor.putStringSet("recent", set)
        editor.apply()
        editor.commit()
    }

    /**
     * Method to get the recent searches from preferences.
     */
    private fun getPreferences(): MutableSet<String>? {
        val prefs = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getStringSet("recent", null)
    }

    /**
     * Handle the Menu items click.
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_two -> setGrid(2)
            R.id.menu_item_three -> setGrid(3)
            R.id.menu_item_four -> setGrid(4)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_grid_number, menu)
        return true
    }

    /**
     * Set the grid as per requirement.
     */
    private fun setGrid(gridSize: Int) {
        currentGridSize = gridSize
        debugLog(gridSize.toString())
        rv_search_results.layoutManager = GridLayoutManager(this, currentGridSize)
    }
}

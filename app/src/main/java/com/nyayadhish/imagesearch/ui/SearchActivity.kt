package com.nyayadhish.imagesearch.ui

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
import com.nyayadhish.imagesearch.R
import com.nyayadhish.imagesearch.data.Items
import com.nyayadhish.imagesearch.debugLog
import com.nyayadhish.imagesearch.dihelper.DIHelper
import com.nyayadhish.imagesearch.viewmodel.ResultListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class SearchActivity : AppCompatActivity() {
    
    private lateinit var mAdapter: SearchResultAdapter
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

        et_search.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty()!!) {
                    mList.clear()
                    mAdapter.notifyDataSetChanged()
                }
            }

        })
    }


    private fun initialiseUI() {
        val factory = DIHelper.provideSearchViewModelFactory(this)
        viewModel = ViewModelProviders.of(this,factory).get(ResultListViewModel::class.java)
        //makeAPICall(startIndex)
        setupRecyclerView()

        rv_search_results.addOnScrollListener(object: EndlessRecyclerViewScrollListener(rv_search_results.layoutManager as GridLayoutManager){
                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    debugLog("page = $page" + "Total Items = " + totalItemsCount)
                    makeAPICall( (totalItemsCount + 1).toString())
                }

                override fun sendLastVisibleItemPosition(
                    lastCompletelyVisibleItemPosition: Int,
                    lastVisibleItemPosition: Int
                ) {
                    //debugLog("SecondMethod => $lastVisibleItemPosition")
                }
            })

    }

    private fun setupRecyclerView() {
        rv_search_results.layoutManager = GridLayoutManager(this, currentGridSize)
        mAdapter = object: SearchResultAdapter(mList){
            override fun onImageClick(
                image: Items,
                ivImage: ImageView
            ) {
                val activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this@SearchActivity , ivImage, "imageMain")
                val intent = Intent(this@SearchActivity, ImageDetailsActivity::class.java)
                intent.putExtra(ImageDetailsActivity.KEY_IMAGE_URL,image.link)
                ContextCompat.startActivity(this@SearchActivity, intent, activityOptionsCompat.toBundle())
            }

        }
        rv_search_results.adapter = mAdapter
    }

    private fun makeAPICall(startIndex: String) {
        viewModel.getSearchResult(et_search.text.toString(), startIndex).observe(this, Observer {
            mList.addAll(it)
            mAdapter.notifyDataSetChanged()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.menu_item_two -> setGrid(2)
            R.id.menu_item_three -> setGrid(3)
            R.id.menu_item_four -> setGrid(4)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_grid_number,menu)
        return true
    }


    private fun setGrid(gridSize: Int) {
        currentGridSize = gridSize
        debugLog(gridSize.toString())
        rv_search_results.layoutManager = GridLayoutManager(this,currentGridSize)
        /*mAdapter = SearchResultAdapter(mList)
        rv_search_results.adapter = mAdapter
        mAdapter.notifyDataSetChanged()*/


    }


}

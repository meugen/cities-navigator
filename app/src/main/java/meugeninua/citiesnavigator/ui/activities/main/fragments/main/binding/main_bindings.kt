package meugeninua.citiesnavigator.ui.activities.main.fragments.main.binding

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import meugeninua.citiesnavigator.R
import meugeninua.citiesnavigator.ui.activities.base.binding.BaseBinding
import meugeninua.citiesnavigator.ui.activities.base.binding.Binding
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.adapters.CitiesAdapter
import meugeninua.citiesnavigator.ui.activities.main.fragments.main.vm.MainData

/**
 * @author meugen
 */
interface MainBinding: Binding {

    fun setupRecycler(listener: CitiesAdapter.OnCitySelectedListener)

    fun displayCities(data: MainData)

    fun displayProgressBar()

    fun displayError(error: Throwable)
}

class MainBindingImpl(private val context: Context): BaseBinding(), MainBinding {

    private lateinit var adapter: CitiesAdapter

    override fun setupRecycler(listener: CitiesAdapter.OnCitySelectedListener) {
        val recycler: RecyclerView = get(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.addItemDecoration(DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL))
        recycler.addItemDecoration(HeaderItemDecoration(context))
        adapter = CitiesAdapter(LayoutInflater.from(context), listener)
        recycler.adapter = adapter
    }

    override fun displayCities(data: MainData) {
        val progressBar: ContentLoadingProgressBar = get(R.id.progress_bar)
        progressBar.hide()
        adapter.swapItems(data.cities)
    }

    override fun displayProgressBar() {
        val progressBar: ContentLoadingProgressBar = get(R.id.progress_bar)
        progressBar.show()
    }

    override fun displayError(error: Throwable) {
        val progressBar: ContentLoadingProgressBar = get(R.id.progress_bar)
        progressBar.hide()
        get<RecyclerView>(R.id.recycler).visibility = View.GONE
        val errorView: TextView = get(R.id.error)
        errorView.text = context.getText(R.string.message_error)
        errorView.visibility = View.VISIBLE
    }
}

private class HeaderItemDecoration(context: Context): RecyclerView.ItemDecoration() {

    private val headerView = View.inflate(context, R.layout.item_country, null) as TextView
    private val headerHeight: Int
    private val bounds = Rect()
    private val paint = Paint()

    init {
        val attrs = intArrayOf(android.R.attr.listPreferredItemHeightSmall)
        val array = context.obtainStyledAttributes(attrs)
        headerHeight = array.getDimensionPixelSize(0, 0)
        array.recycle()
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent!!.getChildAdapterPosition(view)
        if (outRect != null && position % 2 == 0) {
            outRect.top = headerHeight
        }
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(c, parent, state)
        if (c == null || parent == null) {
            return
        }
        paint.color = Color.BLUE

        val count = parent.childCount
        for (index in 0 until count) {
            val view = parent.getChildAt(index)
            if (parent.getChildAdapterPosition(view) % 2 != 0) {
                continue
            }
            parent.getDecoratedBoundsWithMargins(view, bounds)
            headerView.layoutParams = ViewGroup.LayoutParams(bounds.width(), headerHeight)
            headerView.text = "Ukraine"
            headerView.layout(0, 0, bounds.width(), headerHeight)

            c.save()
            c.translate(bounds.left.toFloat(), bounds.top.toFloat())
            headerView.draw(c)
            c.restore()
        }
    }
}
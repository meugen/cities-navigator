package meugeninua.citiesnavigator.ui.activities.base.binding

import android.support.v4.util.SparseArrayCompat
import android.view.View
import java.lang.ref.WeakReference

/**
 * @author meugen
 */
interface Binding {

    fun attachView(view: View)

    fun detachView()

    fun <V: View> get(id: Int): V

    fun has(id: Int): Boolean
}

abstract class BaseBinding {

    private var rootViewRef: WeakReference<View>? = null
    private var childrenViewRefs: SparseArrayCompat<WeakReference<View>>? = null

    fun attachView(view: View) {
        rootViewRef = WeakReference(view)
        childrenViewRefs = SparseArrayCompat()
    }

    fun detachView() {
        rootViewRef = null
        childrenViewRefs?.clear()
        childrenViewRefs = null
    }

    private fun getNullable(id: Int): View? {
        val rootView = rootViewRef?.get() ?: return null
        val refs = childrenViewRefs ?: return null;
        var childView = refs.get(id)?.get()
        if (childView == null) {
            childView = rootView.findViewById(id)
            if (childView != null) {
                refs.put(id, WeakReference(childView))
            }
        }
        return childView
    }

    fun <V: View> get(id: Int): V = getNullable(id) as V?
            ?: throw IllegalArgumentException("View with id $id not found")

    fun has(id: Int): Boolean = getNullable(id) != null
}

class BindingImpl: BaseBinding(), Binding